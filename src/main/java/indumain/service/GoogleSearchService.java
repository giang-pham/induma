package indumain.service;

import indumain.model.InduError;
import indumain.util.FileDownload;
import jodd.io.FileUtil;
import jodd.jerry.Jerry;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GoogleSearchService implements SearchService {
    Predicate<String> SOCIAL = s -> s.contains("jobstreet") || s.contains("bloomberg") || s.contains("facebook");

    @Override
    public CompletableFuture<String> getCompanyUrl(String companyName) throws IOException {
        File file = new File(Files.createTempDirectory("jerry").toString(), companyName);
        URL searchURL = new URL("https://www.google.com/search?q=" + URLEncoder.encode(companyName, "UTF-8"));
        FileDownload.saveUrl(searchURL, file.getPath());

        Jerry doc = Jerry.jerry(FileUtil.readString(file));
        CompletableFuture<String> href = getUrlChrome(doc);
        if(href == null)
        href = getUrlFirefox(doc);
        if(href == null) {
            throw new InduError("No url found for company: " + companyName);
        }
        return href;
    }

    private CompletableFuture<String> getUrlFirefox(Jerry doc) {
        Iterator<Jerry> a = doc.$("a").iterator();
        while(a.hasNext()) {
            Jerry next = a.next();
            String href = next.attr("href");
            if(href != null && !SOCIAL.test(href)) {
                if(href.startsWith("/url?q=")) {

                }
            }
            if(href != null && href.startsWith("/url?q=") && !SOCIAL.test(href)) {
                String url = href
                        .replace("/url?q=", "")
                        .split("&sa")[0];
                return CompletableFuture.completedFuture(url);
            }
        }
        return null;
    }

    private CompletableFuture<String> getUrlChrome(Jerry doc) {
        Iterator<Jerry> urls = doc.$("h3 a").iterator();
        while(urls.hasNext()) {
            Jerry next = urls.next();
            String href = next.attr("href");
            if(href != null && !href.startsWith("/url?") && !href.startsWith("/url?") && !SOCIAL.test(href)) {
                Pattern p = Pattern.compile("http[^&]*");
                Matcher m = p.matcher(href);
                if(m.find()) {
                    System.out.println("url: " + m.group());
                    return CompletableFuture.completedFuture(m.group());
                }
            }
        }
        return null;
    }

    @Override
    public CompletableFuture<String> getContent(String url) {
        if(StringUtils.isEmpty(url)) throw new InduError("url is empty");
        try {
            if(!url.startsWith("http")) {
                url = "http://" + url;
            }
            URL searchURL = new URL(url);
            String content = FileDownload.readUrl(searchURL);
            return CompletableFuture.completedFuture(content);
        } catch (IOException e) {
            throw new InduError("cannot read url: " + url);
        }
    }

    public static void main(String[] args) {
        String s = "/url?q=https://www.onkyo.com/companyprofile/&sa=U&ved=0ahUKEwit9-iOr-rcAhUYAogKHf4PBigQFggUMAA&usg=AOvVaw2hI6p9_xbsTv_rNEAjLjRG";
        System.out.println(s.replace("/url?q=", "")
                .split("&sa")[0]);
        System.out.println(s.startsWith("/url?q="));

    }
}

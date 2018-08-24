package indumain.util;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileDownload {
    public static boolean saveUrl(URL url, String filePath) {

        boolean isSucceed = true;

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url.toString());
        httpGet.addHeader("User-Agent", RandomUserAgent.getRandomUserAgent());

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity imageEntity = httpResponse.getEntity();
            if (imageEntity != null) {
                FileUtils.copyInputStreamToFile(imageEntity.getContent(), new File(filePath));
            }

        } catch (IOException e) {
            isSucceed = false;
        }
        httpGet.releaseConnection();

        return isSucceed;
    }

    public static String readUrl(URL url) {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url.toString());
        httpGet.addHeader("User-Agent", RandomUserAgent.getRandomUserAgent());

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity imageEntity = httpResponse.getEntity();
            if (imageEntity != null) {
                return EntityUtils.toString(imageEntity);
            }

        } catch (IOException e) {

        }
        httpGet.releaseConnection();

        return null;
    }
}
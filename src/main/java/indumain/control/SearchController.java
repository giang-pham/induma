package indumain.control;

import indumain.model.Result;
import indumain.service.MatchService;
import indumain.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
public class SearchController {

    final SearchService searchService;
    final MatchService matchService;

    @Autowired
    public SearchController(SearchService searchService, MatchService matchService) {
        this.searchService = searchService;
        this.matchService = matchService;
    }

    @GetMapping("/search")
    public CompletableFuture<Result> findIndustry (
            @RequestParam("cName") String companyName) throws IOException, InterruptedException {
        CompletableFuture<String> urlStage = searchService.getCompanyUrl(companyName);
        return urlStage.thenComposeAsync(url -> searchService.getContent(url))
                .thenComposeAsync(c -> matchService.getIndustry(c))
                .thenCombine(urlStage, (industry, url) -> new Result(url, industry));
    }
}

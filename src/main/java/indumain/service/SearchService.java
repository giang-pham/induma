package indumain.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface SearchService {
    CompletableFuture<String> getCompanyUrl(String companyName) throws IOException, InterruptedException;
    CompletableFuture<String> getContent(String url);
}

package indumain.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface SearchService {
    CompletableFuture<String> getCompanyUrl(String companyName) throws IOException, InterruptedException, ExecutionException;
    CompletableFuture<String> getContent(String url);
}

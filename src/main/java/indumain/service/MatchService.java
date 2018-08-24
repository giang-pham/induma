package indumain.service;

import java.util.concurrent.CompletableFuture;

public interface MatchService {
    CompletableFuture<String> getIndustry(String c);
}

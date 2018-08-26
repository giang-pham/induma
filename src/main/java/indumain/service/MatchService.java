package indumain.service;

import indumain.rule.IndustryRule;

import java.util.concurrent.CompletableFuture;

public interface MatchService {
    CompletableFuture<String> getIndustry(String c, IndustryRule rule);
}

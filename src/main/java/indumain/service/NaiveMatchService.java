package indumain.service;

import indumain.rule.IndustryRule;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

@Service
public class NaiveMatchService implements MatchService {

    @Override
    public CompletableFuture<String> getIndustry(String c, IndustryRule rule) {
        if(StringUtils.isEmpty(c)) return CompletableFuture.completedFuture("NOT_FOUND");
        if(rule.match(c)) return CompletableFuture.completedFuture(rule.getLabel());
        return CompletableFuture.completedFuture("NOT_FOUND");
    }
}

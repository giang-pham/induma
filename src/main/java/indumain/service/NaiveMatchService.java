package indumain.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

@Service
public class NaiveMatchService implements MatchService {

    Predicate<String> CAR_INDUSTRY = (s) ->
                    s.contains("automotive") || s.contains("automobile") ||
                    s.contains("car") || s.contains("audio") ||
                    s.contains("entertainment");

    Map<String, Predicate> MAP = new HashMap<String, Predicate>() {{
        put("CAR_AUDIO", CAR_INDUSTRY);
    }};

    @Override
    public CompletableFuture<String> getIndustry(String c) {
        if(StringUtils.isEmpty(c)) return CompletableFuture.completedFuture("NOT_FOUND");
        String industry = MAP.entrySet().stream()
                .filter(e -> e.getValue().test(c.toLowerCase()))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse("NOT_FOUND");
        System.out.println(industry);

        return CompletableFuture.completedFuture(industry);
    }
}

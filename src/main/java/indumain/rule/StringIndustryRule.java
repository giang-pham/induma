package indumain.rule;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringIndustryRule extends IndustryRule<String> {
    private String label;
    private String rule;
    private final Predicate<String> matcher;

    public StringIndustryRule(String label, String rule) {
        this.label = label;
        this.rule = rule;
        matcher = getMatcher();
    }

    @Override
    public boolean match(String c) {
        return matcher.test(c);
    }

    public String getLabel() {
        return label;
    }

    public String getRule() {
        return rule;
    }

    private Predicate<String> getMatcher() {
        String[] split = rule.split("\"\\sOR\\s\"");
        List<Pattern> collect = Arrays.stream(split)
                .map(s -> s.replace("\"", ""))
                .map(s -> Pattern.compile(".*\\b" + s + "\\b.*", Pattern.CASE_INSENSITIVE))
                .collect(Collectors.toList());
        return s -> collect.stream()
                .filter(m -> m.matcher(s).find())
                .count() > 0;
    }

}

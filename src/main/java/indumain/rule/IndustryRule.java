package indumain.rule;

import lombok.Data;

import java.util.function.Predicate;

public abstract class IndustryRule<T> {
    private String label;
    private String rule;
    private Predicate<T> matcher;
    public abstract boolean match(T c);

    public String getLabel() {
        return label;
    }
}

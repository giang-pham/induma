package indumain.rule;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringIndustryRuleTest {
    @Test
    public void when_rule_is_correct_return_industry() {
        StringIndustryRule rule = new StringIndustryRule("FOOD", "\"FOO\" OR \"BAR\"");
        boolean actual = rule.match("FOOawdnajkwd alwdm aklmwdkaiunraind ai");
        assertThat(actual).isFalse();
        actual = rule.match("FOO awdnajkwd alwdm aklmwdkaiunraind ai");
        assertThat(actual).isTrue();
    }
}

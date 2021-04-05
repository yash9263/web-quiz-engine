package engine;

import javax.persistence.Embeddable;

@Embeddable
public class Options {
    private String option;

    public Options() {}

    public Options(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}

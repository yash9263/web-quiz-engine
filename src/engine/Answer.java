package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Answer {

    private Integer answer;

    public Answer() {}

    public Answer(Integer answer) {
        this.answer = answer;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}

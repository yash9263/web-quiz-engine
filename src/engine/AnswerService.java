package engine;

import java.util.ArrayList;
import java.util.List;

public class AnswerService {
    private List<Integer> answer;

    public AnswerService() {
        this.answer = new ArrayList<>();
    }
    public AnswerService(List<Integer> answer) {
        this.answer = answer;
    }

    public List<Integer> getAnswer() {
        return answer;
    }
}

package engine;

public class Solve {
    private boolean success;
    private String feedback;

    Solve() {}

    Solve (boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}

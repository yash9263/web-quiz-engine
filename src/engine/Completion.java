package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Completion {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int completionId;

    @JsonIgnore
    String username;

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private int quizId;

    @JsonProperty(value = "completedAt", access = JsonProperty.Access.READ_ONLY)
    private Timestamp timestamp;

    public Completion(){}

    public Completion(String username, int quizId, long timestamp) {
        this.username = username;
        this.quizId = quizId;
        this.timestamp = new Timestamp(timestamp);
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int id) {
        this.quizId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

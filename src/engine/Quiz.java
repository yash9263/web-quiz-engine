package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @NotNull
    @Size(min = 2, max = 4)
    @ElementCollection
    @CollectionTable(name = "quizOptions", joinColumns = @JoinColumn(name = "quizId"))
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    @CollectionTable(name = "quizAnswers", joinColumns = @JoinColumn(name = "quizId"))
    private List<Integer> answer;

    @JsonIgnore
    private String author;

    public Quiz() {
    }

    public Quiz(String title, String text, List<String> options, String author) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = new ArrayList<>();
        this.author = author;
    }
    public Quiz(String title, String text, List<String> options, List<Integer> answer, String author) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
        this.author = author;
    }

    public long getId() {return id;};

    @Column(name = "quizId")
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "text", nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "options", nullable = false)
    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }


    @JsonIgnore
    @Column(name = "answer")
    public List<Integer> getAnswer() { return answer; }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

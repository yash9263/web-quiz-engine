package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompletionsRepository completionsRepository;

    @GetMapping("/api/quizzes")
    public ResponseEntity<Page<Quiz>> getAllQuiz (@RequestParam(name = "page", defaultValue = "0", required = false) Integer pageNo,
                                                  @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                                  @RequestParam(defaultValue = "id", required = false) String sortBy) {
        Page<Quiz> list = quizService.getAllQuiz(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public Solve getAnswer(@PathVariable("id") long id, @RequestBody(required = false) AnswerService answer, Authentication authentication) {
        if(answer == null) {
            answer = new AnswerService();
        }
        List<Integer> postedAnswers = answer.getAnswer();
        List<Integer> getQuizAnswer;

        try {
            getQuizAnswer = quizRepository.getOne(id).getAnswer();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
        }
//        System.out.println(quizRepository.getOne(id).getAnswer());
//        System.out.println(getQuizAnswer);
//        System.out.println(postedAnswers);
        if(postedAnswers.equals(getQuizAnswer)) {
            Completion completion = new Completion(authentication.getName(), (int)id, System.currentTimeMillis());
            completionsRepository.save(completion);
            return new Solve(true, "Congratulations, you're right!");
        } else {
            return new Solve(false, "Wrong answer! Please, try again.");
        }
    }


    @PostMapping(value = "/api/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Quiz postBody(@Valid @RequestBody Quiz quiz, Authentication authentication) {

        if(quiz.getAnswer() == null) {
            return quizRepository.save(new Quiz(quiz.getTitle(), quiz.getText(), quiz.getOptions(), authentication.getName()));
        } else  {
            return quizRepository.save(new Quiz(quiz.getTitle(), quiz.getText(), quiz.getOptions(), quiz.getAnswer(), authentication.getName()));
        }
    }

    @GetMapping(value = "/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable("id") Long id) throws ResponseStatusException {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(quiz);
    }

    @PostMapping(value = "/api/register")
    public void registerUser(@RequestBody @Valid User user) throws ResponseStatusException {
        userService.registerUser(user);
    }

    @DeleteMapping(value = "/api/quizzes/{id}")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable("id") Long id, Authentication authentication) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(!quiz.getAuthor().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizRepository.delete(quiz);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<Completion>> getCompleted (@RequestParam(name = "page", defaultValue = "0", required = false) Integer pageNo,
                                                          @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                                          Authentication authentication) {
        Page<Completion> page = quizService.getAllCompletions(pageNo, pageSize, authentication.getName());
        return new ResponseEntity<>(page, new HttpHeaders(), HttpStatus.OK);
    }
}


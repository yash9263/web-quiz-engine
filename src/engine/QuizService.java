package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    CompletionsRepository completionsRepository;

    public Page<Quiz> getAllQuiz(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Quiz> quizPage = quizRepository.findAll(paging);

        return quizPage;

    }

    public Page<Completion> getAllCompletions(Integer pageNo, Integer pageSize, String userName) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("timestamp").descending());
        Page<Completion> completionsPage = completionsRepository.getCompletions(userName, paging);

        return completionsPage;
    }
}

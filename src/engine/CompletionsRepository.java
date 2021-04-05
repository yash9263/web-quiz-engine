package engine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionsRepository extends JpaRepository<Completion, Integer> {
    @Query(value = "SELECT * FROM Completion WHERE username = ?1", nativeQuery = true)
    public Page<Completion> getCompletions(String username, Pageable pageable) ;
}

package noemibaglieri.repositories;

import noemibaglieri.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByEmail(String email);
}

package noemibaglieri.repositories;

import noemibaglieri.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostsRepository extends JpaRepository<BlogPost, Long> {
}

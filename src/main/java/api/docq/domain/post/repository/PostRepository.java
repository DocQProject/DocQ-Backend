package api.docq.domain.post.repository;

import api.docq.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.isDeleted = false")
    Page<Post> findAllNotDeleted(Pageable pageable);
}

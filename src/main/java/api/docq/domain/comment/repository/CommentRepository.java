package api.docq.domain.comment.repository;

import api.docq.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.isDeleted = false ")
    List<Comment> findCommentsByPostId(Long postId);
}

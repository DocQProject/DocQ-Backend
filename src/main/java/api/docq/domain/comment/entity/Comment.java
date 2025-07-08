package api.docq.domain.comment.entity;

import api.docq.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long postId;

    private String content;

    private boolean isDeleted;

    @Builder
    private Comment(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.isDeleted = false;
    }

    public static Comment of(Long userId, Long postId, String content) {
        return Comment.builder()
                .userId(userId)
                .postId(postId)
                .content(content)
                .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void deleteComment() {
        this.isDeleted = true;
    }
}

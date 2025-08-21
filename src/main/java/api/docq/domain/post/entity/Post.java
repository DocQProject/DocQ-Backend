package api.docq.domain.post.entity;

import api.docq.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
public class Post extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private String author;

    private Integer viewCount;

    @Lob
    private String content;

    private boolean isDeleted;

    @Builder
    private Post(Long userId, String title, String author,String content) {
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.viewCount = 0;
        this.isDeleted = false;
    }

    public static Post of(Long userId, String title, String author, String content) {
        return Post.builder()
                .title(title)
                .author(author)
                .content(content)
                .userId(userId)
                .build();
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void deletePost() {
        this.isDeleted = true;
    }
}

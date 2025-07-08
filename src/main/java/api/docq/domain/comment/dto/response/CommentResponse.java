package api.docq.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final String author;

    private final String content;

    private final LocalDateTime createdAt;

    @Builder
    private CommentResponse(String author, String content, LocalDateTime createdAt) {
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static CommentResponse of(String author, String content, LocalDateTime createdAt) {
        return CommentResponse.builder()
                .author(author)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}

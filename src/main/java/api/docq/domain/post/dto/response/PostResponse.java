package api.docq.domain.post.dto.response;

import api.docq.domain.comment.dto.response.CommentResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponse {

    private final String title;

    private final String content;

    private final String author;

    private final Integer viewCount;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final List<CommentResponse> comments;

    @Builder
    private PostResponse(String title, String content, String author, Integer viewCount, LocalDateTime createdAt, LocalDateTime updatedAt, List<CommentResponse> comments) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comments = comments;
    }

    public static PostResponse of(String title, String content, String author, Integer viewCount, LocalDateTime createdAt, LocalDateTime updatedAt, List<CommentResponse> comments) {
        return PostResponse.builder()
                .title(title)
                .content(content)
                .author(author)
                .viewCount(viewCount)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .comments(comments)
                .build();
    }

}

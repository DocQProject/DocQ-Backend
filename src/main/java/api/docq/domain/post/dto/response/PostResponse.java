package api.docq.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final String title;

    private final String content;

    private final String author;

    private final Integer viewCount;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    @Builder
    private PostResponse(String title, String content, String author, Integer viewCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostResponse of(String title, String content, String author, Integer viewCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return PostResponse.builder()
                .title(title)
                .content(content)
                .author(author)
                .viewCount(viewCount)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}

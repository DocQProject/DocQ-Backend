package api.docq.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponse {

    private final String title;

    private final String author;

    private final String content;

    private final Integer viewCount;

    private final LocalDateTime createdAt;

    @Builder
    private PostListResponse(String title, String author, String content, Integer viewCount, LocalDateTime createdAt) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
    }

    public static PostListResponse of(String title, String author, String content, Integer viewCount, LocalDateTime createdAt) {
        return PostListResponse.builder()
                .title(title)
                .author(author)
                .content(content)
                .viewCount(viewCount)
                .createdAt(createdAt)
                .build();
    }
}

package api.docq.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostListResponse {

    private final Long id;

    private final String title;

    private final String author;

    private final String content;

    private final Integer viewCount;

    private final String createdAt;

    @Builder
    private PostListResponse(Long id, String title, String author, String content, Integer viewCount, String createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
    }

    public static PostListResponse of(Long id, String title, String author, String content, Integer viewCount, String createdAt) {
        return PostListResponse.builder()
                .id(id)
                .title(title)
                .author(author)
                .content(content)
                .viewCount(viewCount)
                .createdAt(createdAt)
                .build();
    }
}

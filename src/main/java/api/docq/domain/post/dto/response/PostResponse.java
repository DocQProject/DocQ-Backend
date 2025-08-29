package api.docq.domain.post.dto.response;

import api.docq.domain.comment.dto.response.CommentResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponse {

    private final Long id;

    private final String title;

    private final String content;

    private final String author;

    private final Integer viewCount;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final List<CommentResponse> comments;

    private final List<String> imageURLs;

    @Builder
    private PostResponse(Long id, String title, String content, String author, Integer viewCount, LocalDateTime createdAt, LocalDateTime updatedAt, List<CommentResponse> comments, List<String> imageURLs) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comments = comments;
        this.imageURLs = imageURLs;
    }

    public static PostResponse of(Long id, String title, String content, String author, Integer viewCount, LocalDateTime createdAt, LocalDateTime updatedAt, List<CommentResponse> comments, List<String> imageURLs) {
        return PostResponse.builder()
                .id(id)
                .title(title)
                .content(content)
                .author(author)
                .viewCount(viewCount)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .comments(comments)
                .imageURLs(imageURLs)
                .build();
    }

}

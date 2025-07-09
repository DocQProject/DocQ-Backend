package api.docq.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponse {

    private final String author;

    private final String content;

    private final Integer starPoint;

    private final List<String> imageURLs;

    private final LocalDateTime createdAt;

    @Builder
    private ReviewResponse(String author, String content, Integer starPoint, List<String> imageURLs, LocalDateTime createdAt) {
        this.author = author;
        this.content = content;
        this.starPoint = starPoint;
        this.imageURLs = imageURLs;
        this.createdAt = createdAt;
    }

    public static ReviewResponse of(String author, String content, Integer starPoint, List<String> imageURLs, LocalDateTime createdAt) {
        return ReviewResponse.builder()
                .author(author)
                .content(content)
                .starPoint(starPoint)
                .imageURLs(imageURLs)
                .createdAt(createdAt)
                .build();
    }
}

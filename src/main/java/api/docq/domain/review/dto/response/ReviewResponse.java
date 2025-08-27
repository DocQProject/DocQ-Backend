package api.docq.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponse {

    private final Long reviewId;

    private final String author;

    private final String content;

    private final Integer starPoint;

    private final List<String> imageURLs;

    private final String createdAt;

    @Builder
    private ReviewResponse(Long reviewId, String author, String content, Integer starPoint, List<String> imageURLs, String createdAt) {
        this.reviewId = reviewId;
        this.author = author;
        this.content = content;
        this.starPoint = starPoint;
        this.imageURLs = imageURLs;
        this.createdAt = createdAt;
    }

    public static ReviewResponse of(Long reviewId, String author, String content, Integer starPoint, List<String> imageURLs, String createdAt) {
        return ReviewResponse.builder()
                .reviewId(reviewId)
                .author(author)
                .content(content)
                .starPoint(starPoint)
                .imageURLs(imageURLs)
                .createdAt(createdAt)
                .build();
    }
}

package api.docq.domain.review.entity;


import api.docq.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
public class Review extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long clinicId;

    private String author;

    private String content;

    private Integer starPoint;

    @Builder
    private Review(Long userId, Long clinicId,String author, String content, Integer starPoint) {
        this.userId = userId;
        this.clinicId = clinicId;
        this.author = author;
        this.content = content;
        this.starPoint = starPoint;
    }

    public static Review of(Long userId, Long clinicId, String content, String author, Integer starPoint) {
        return Review.builder()
                .userId(userId)
                .clinicId(clinicId)
                .author(author)
                .content(content)
                .starPoint(starPoint)
                .build();
    }

    public void updateContentAndStar(String content, Integer starPoint) {
        this.content = content;
        this.starPoint = starPoint;
    }
}

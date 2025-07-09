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

    private String content;

    private Integer starPoint;

    @Builder
    private Review(Long userId, Long clinicId, String content, Integer starPoint) {
        this.userId = userId;
        this.clinicId = clinicId;
        this.content = content;
        this.starPoint = starPoint;
    }

    public static Review of(Long userId, Long clinicId, String content, Integer starPoint) {
        return Review.builder()
                .userId(userId)
                .clinicId(clinicId)
                .content(content)
                .starPoint(starPoint)
                .build();
    }
}

package api.docq.domain.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 5, max = 200, message = "리뷰는 최소 5자 이상, 200자 이하로 작성해주세요.")
    private final String content;

    @Min(value = 1, message = "별점은 최소 1점 이상이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점까지만 선택할 수 있습니다.")
    private final Integer starPoint;
    
    public ReviewRequest(String content, Integer starPoint) {
        this.content = content;
        this.starPoint = starPoint;
    }
}

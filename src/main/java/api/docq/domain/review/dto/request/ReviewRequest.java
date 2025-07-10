package api.docq.domain.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewRequest {

    @NotBlank(message = "내용을 입력하세요")
    @Size(min = 5, max = 200)
    private final String content;
    
    @Size(min = 1, max = 5)
    private final Integer starPoint;
    
    public ReviewRequest(String content, Integer starPoint) {
        this.content = content;
        this.starPoint = starPoint;
    }
}

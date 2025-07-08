package api.docq.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequest {

    @NotBlank(message = "내용을 입력하세요")
    @Size(min = 5, max = 200)
    private final String content;

    public CommentRequest(String content) {
        this.content = content;
    }
}

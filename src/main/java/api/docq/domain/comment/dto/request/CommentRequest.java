package api.docq.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequest {

    @NotBlank(message = "내용을 입력하세요")
    private final String content;

    public CommentRequest(String content) {
        this.content = content;
    }
}

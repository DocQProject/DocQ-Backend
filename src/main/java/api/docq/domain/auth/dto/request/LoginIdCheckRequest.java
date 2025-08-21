package api.docq.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginIdCheckRequest {

    @NotBlank(message = "loginId를 입력해주세요.")
    private final String loginId;
}

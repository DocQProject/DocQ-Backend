package api.docq.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDeleteRequest {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;
}

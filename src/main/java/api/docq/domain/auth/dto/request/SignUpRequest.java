package api.docq.domain.auth.dto.request;

import api.docq.domain.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private final String loginId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private final String name;

    @NotBlank(message = " 비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$", message = "비밀번호 형식이 올바르지 않습니다. 8자 이상, 대소문자 포함, 숫자 및 특수문자(@$!%*?&#) 포함")
    private final String password;

    @NotBlank(message= "비밀번호 재입력은 필수 입력값입니다.")
    private final String checkPassword;

    @Email
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @NotNull(message = "권한은 필수 입력값입니다.")
    private final UserRole role;
}

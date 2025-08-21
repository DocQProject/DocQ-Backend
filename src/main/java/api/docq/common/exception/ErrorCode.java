package api.docq.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //CONFLICT
    ALREADY_EXISTS_USER(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
    ALREADY_EXISTS_LOGIN_ID(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),

    //NOT_FOUND
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),

    //BAD_REQUEST


    //FORBIDDEN


    //UNAUTHORIZED
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus errorCode;
    private final String errorMessage;

}
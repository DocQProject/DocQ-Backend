package api.docq.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{
    private HttpStatus status;
    private String message;
    
    public CustomException(ErrorCode errorCode) {
        super();
        this.status = errorCode.getErrorCode();
        this.message = errorCode.getErrorMessage();
    }
}

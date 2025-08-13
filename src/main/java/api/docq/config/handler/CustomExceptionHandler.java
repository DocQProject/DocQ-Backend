package api.docq.config.handler;

import api.docq.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> exceptionHandler(CustomException ce) {
        HttpStatus status = ce.getStatus();
        String message = ce.getMessage();

        return ResponseEntity.status(status).body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> exceptionHandler(MethodArgumentNotValidException me) {
        List<FieldError> fieldErrors = me.getBindingResult().getFieldErrors();
        List<String> fieldErrorList = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)  // 각 필드의 오류 메시지를 가져온다.
                .toList();

        return ResponseEntity.status(me.getStatusCode()).body(fieldErrorList);
    }
}

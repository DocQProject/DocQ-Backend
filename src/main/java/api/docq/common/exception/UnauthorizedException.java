package api.docq.common.exception;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

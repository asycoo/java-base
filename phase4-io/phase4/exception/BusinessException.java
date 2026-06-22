package phase4.exception;

/** 业务可预期异常，如「书已借出」 */
public class BusinessException extends AppException {

    public BusinessException(String errorCode, String message) {
        super(errorCode, message);
    }
}

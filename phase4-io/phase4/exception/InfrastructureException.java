package phase4.exception;

/** IO / 网络 / 外部依赖失败 */
public class InfrastructureException extends AppException {

    public InfrastructureException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}

package com.asycoo.library.exception;

import com.asycoo.library.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理 — 所有 Controller 抛出的异常在此统一转成 ApiResponse
 *
 * 类似前端的 axios 响应拦截器：集中处理 4xx/5xx
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException ex) {
        HttpStatus status = switch (ex.getErrorCode()) {
            case "BOOK_NOT_FOUND", "MEMBER_NOT_FOUND", "LOAN_NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "BOOK_DUPLICATE", "MEMBER_DUPLICATE", "USERNAME_DUPLICATE" -> HttpStatus.CONFLICT;
            case "BOOK_NOT_AVAILABLE", "AUTH_FAILED" -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.BAD_REQUEST;
        };
        return ResponseEntity.status(status)
                .body(ApiResponse.fail(toNumericCode(ex.getErrorCode()), ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(ValidationException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(4001, ex.getMessage()));
    }

    /** @Valid 注解校验失败（如 @NotBlank 为空） */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("参数校验失败");
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(4001, message));
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ApiResponse<Void>> handleInfrastructure(InfrastructureException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(5000, ex.getMessage()));
    }

    /** 未预料异常兜底 — 避免返回 Spring 默认白板错误页 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnknown(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(5000, "服务器内部错误"));
    }

    private static int toNumericCode(String errorCode) {
        return switch (errorCode) {
            case "BOOK_NOT_FOUND" -> 4041;
            case "BOOK_DUPLICATE" -> 4091;
            case "MEMBER_DUPLICATE" -> 4092;
            case "USERNAME_DUPLICATE" -> 4093;
            case "BOOK_NOT_AVAILABLE" -> 4002;
            case "AUTH_FAILED" -> 4003;
            case "MEMBER_NOT_FOUND" -> 4042;
            case "LOAN_NOT_FOUND" -> 4043;
            case "VALIDATION_ERROR" -> 4001;
            default -> 5000;
        };
    }
}

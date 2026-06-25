package com.asycoo.library.exception;

/**
 * 应用异常基类 — 与 phase4 相同设计，统一 errorCode + message
 */
public abstract class AppException extends RuntimeException {

    private final String errorCode;

    protected AppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected AppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

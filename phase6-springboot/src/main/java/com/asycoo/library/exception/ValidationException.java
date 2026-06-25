package com.asycoo.library.exception;

/** 业务层参数校验失败 */
public class ValidationException extends AppException {

    public ValidationException(String message) {
        super("VALIDATION_ERROR", message);
    }
}

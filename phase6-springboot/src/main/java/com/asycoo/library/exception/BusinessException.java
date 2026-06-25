package com.asycoo.library.exception;

/** 业务可预期异常，如「图书不存在」「ID 已存在」 */
public class BusinessException extends AppException {

    public BusinessException(String errorCode, String message) {
        super(errorCode, message);
    }
}

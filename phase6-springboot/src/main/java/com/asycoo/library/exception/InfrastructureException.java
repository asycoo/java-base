package com.asycoo.library.exception;

/** IO / 网络 / 外部依赖失败（Week 8 换 JPA/文件时用到） */
public class InfrastructureException extends AppException {

    public InfrastructureException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}

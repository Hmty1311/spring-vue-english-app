package com.engapp.backend.common.exception;

public class AuthenticationException extends RuntimeException {

    private final String errorCode;

    public AuthenticationException(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
package com.engapp.backend.domain.common.exception;

public class AuthenticationException extends RuntimeException {

    private String errorCode;

    public AuthenticationException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public AuthenticationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}

package com.engapp.backend.common.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleAuthenticationException(){
        return Map.of(
            "error", "AUTH0-001",
            "message", "ログインIDまたはパスワードが正しくありません"
        );
    }

}

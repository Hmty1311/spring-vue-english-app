package com.engapp.backend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(AuthenticationException ex){
        return new ErrorResponse(ex.getErrorCode(), "ログインIDまたはパスワードが正しくありません");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex){
        return new ErrorResponse("VALID-001", "入力値が不正です");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            ConstraintViolationException e
    ) {

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        "VALIDATION_ERROR",
                        e.getMessage()
                ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
        Exception ex
    ){

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                    "SYSTEM_ERROR",
                    "予期せぬエラーが発生しました"
                ));
    }
}

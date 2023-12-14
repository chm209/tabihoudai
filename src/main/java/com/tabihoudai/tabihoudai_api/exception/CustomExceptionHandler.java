package com.tabihoudai.tabihoudai_api.exception;

import com.tabihoudai.tabihoudai_api.dto.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e){
        return ResponseEntity.status(e.getStatus()).body(new ExceptionResponse(e.getStatus().value(), e.getMessage()));
    }
}

package com.tabihoudai.tabihoudai_api.exception;

import com.tabihoudai.tabihoudai_api.dto.ExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionDTO.ExceptionResponse> handleCustomExceptoin(CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(new ExceptionDTO.ExceptionResponse(e.getStatus().value(), e.getMessage()));
    }
}

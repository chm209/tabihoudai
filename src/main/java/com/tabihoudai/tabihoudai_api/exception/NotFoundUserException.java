package com.tabihoudai.tabihoudai_api.exception;

import org.springframework.http.HttpStatus;

public class NotFoundUserException extends CustomException{
    public NotFoundUserException() {
        super(HttpStatus.NOT_FOUND, "잘못된 회원 정보");
    }

    public static final CustomException EXCEPTION = new NotFoundUserException();
}

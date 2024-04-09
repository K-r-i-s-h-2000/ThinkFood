package com.thinkpalm.thinkfood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class ValueNullException extends RuntimeException{
    public ValueNullException(String message) {
        super(message);

    }
}

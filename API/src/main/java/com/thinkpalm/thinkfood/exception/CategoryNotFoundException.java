package com.thinkpalm.thinkfood.exception;



import java.io.Serial;

public class CategoryNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1234L;

    public CategoryNotFoundException(String message) {

        super(message);
    }
}



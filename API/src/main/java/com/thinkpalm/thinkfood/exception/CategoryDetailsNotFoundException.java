package com.thinkpalm.thinkfood.exception;


import java.io.Serial;

public class CategoryDetailsNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1234L;

    public CategoryDetailsNotFoundException(String message) {

        super(message);
    }
}

package com.thinkpalm.thinkfood.exception;

import java.io.Serial;

public class CouponCodeAlreadyExistsException extends Exception{
    @Serial
    private static final long serialVersionUID = 1234;
    public CouponCodeAlreadyExistsException(String message) {

        super(message);
    }
}

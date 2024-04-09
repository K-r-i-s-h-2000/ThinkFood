package com.thinkpalm.thinkfood.exception;

import java.io.Serial;
/**
 * Custom exception class for indicating that a resource was not found.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */
public class NotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID =1234L;
    /**
     * Constructs a new `NotFoundException` with the specified error message.
     *
     * @param message A String containing the error message associated with this exception.
     */
    public NotFoundException(String message){
        super(message);
    }
}

package com.thinkpalm.thinkfood.exception;
/**
 * Custom exception class for indicating that an item is not available.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */

public class NotAvailableException extends RuntimeException{
    /**
     * Constructs a new `NotAvailableException` with the specified error message.
     *
     * @param message A String containing the error message associated with this exception.
     */
    public NotAvailableException(String message){
        super(message);
    }
}

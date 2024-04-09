package com.thinkpalm.thinkfood.exception;
/**
 * Custom exception class for indicating that items in a cart belong to different restaurants.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */
public class NotSameRestaurantException extends RuntimeException{
    /**
     * Constructs a new `NotSameRestaurantException` with the specified error message.
     *
     * @param message A String containing the error message associated with this exception.
     */
    public NotSameRestaurantException(String message){
        super(message);
    }
}

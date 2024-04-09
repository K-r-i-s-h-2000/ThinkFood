/**
 * Delivery Details Missing Exception class for delivery agents CRUD operations.
 * This class is used to throw exception when not null fields receive null values.
 * @author: Rinkle Rose Renny
 * @since : 26 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class DetailsMissingException extends NullPointerException{

    /**
     * @param message which send that Delivery details are missing.
     */
    public DetailsMissingException(String message)
    {
        super(message);
    }
}

/**
 * Delivery Details Not Found Exception class for delivery agents CRUD operations.
 * This class is used to throw exception when records can not be found.
 * @author: Rinkle Rose Renny
 * @since : 26 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class DetailsNotFoundException extends RuntimeException {

    /**
     * @param message which send Delivery details not found.
     */
    public DetailsNotFoundException(String message)
    {
        super(message);
    }
}

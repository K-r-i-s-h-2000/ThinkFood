package com.thinkpalm.thinkfood.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Model class representing customer information.
 *
 * <p>The `Customer` class encapsulates various attributes related to a customer, including their
 * address, geographical coordinates, date of birth, gender, name, and email.
 *
 * @author ajay.s
 * @version [Date]
 * @since [Date]
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer{
    /**
     * The unique identifier of the customer.
     */
    private String id;
    /**
     * The address of the customer.
     */
    private String customerAddress;
    /**
     * The longitude of the customer's location.
     */
    private Double customerLongitude;
    /**
     * The latitude of the customer's location.
     */
    private Double customerLatitude;
    /**
     * The date of birth of the customer.
     */
    private Date customerDateOfBirth;
    /**
     * The gender of the customer.
     */
    private String customerGender;
    /**
     * The name of the customer.
     */
    private String customerName;
    /**
     * The email address of the customer.
     */
    private String email;
    /**
     * The user ID associated with the customer.
     */

    private Boolean isActive=false;
    private String image;
    private String customer_name;
}

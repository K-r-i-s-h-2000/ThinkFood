package com.thinkpalm.thinkfood.model;


import lombok.*;

/**
 * Model class representing a registration request.
 *
 * <p>The `RegisterRequest` class contains information required for user registration, including
 * the user's first name, last name, email, password, role, and additional details specific to the
 * user's role (customer, restaurant owner, or delivery agent).
 *
 * @author ajay.S
 * @version 2.0
 * @since 31/10/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterRequest {
    /**
     * The first name of the user.
     */
    private String firstname;
    /**
     * The last name of the user.
     */
    private String lastname;
    /**
     * The email address of the user, which serves as their unique identifier.
     */
    private String email;
    /**
     * The user's password for authentication.
     */
    private String password;
    /**
     * An integer representing the user's role. Valid role values:
     * - 1: Customer
     * - 2: Restaurant Owner
     * - 3: Delivery Agent
     */
    private int role;

    /**
     * Additional information specific to a customer user.
     */
    private Customer customer;
    /**
     * Additional information specific to a restaurant owner user.
     */
    private Restaurant restaurant;
    /**
     * Additional information specific to a delivery agent user.
     */
    private Delivery delivery;

}

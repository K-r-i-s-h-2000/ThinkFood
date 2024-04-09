package com.thinkpalm.thinkfood.model;

import lombok.*;

/**
 * Model class representing an authentication request.
 *
 * <p>The `AuthenticationRequest` class contains information required for user authentication, including
 * the user's email and password.
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
public class AuthenticationRequest {
    /**
     * The email address of the user, which serves as their unique identifier.
     */
    private String email;
    /**
     * The user's password for authentication.
     */
    private String password;
    /**
     * A message related to the authentication request.
     */
    private String message;


    public AuthenticationRequest(String userEmail, String userPassword) {
    }
}

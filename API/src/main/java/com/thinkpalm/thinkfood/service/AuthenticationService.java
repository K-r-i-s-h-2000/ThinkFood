package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.AuthenticationRequest;
import com.thinkpalm.thinkfood.model.AuthenticationResponse;
import com.thinkpalm.thinkfood.model.DeletionRequest;
import com.thinkpalm.thinkfood.model.RegisterRequest;
import org.springframework.http.ResponseEntity;

/**
 * Service interface for authentication and user registration.
 *  @author ajay.S
 *  @version 2.0
 *  @since 31/10/2023
 */
public interface AuthenticationService {
    /**
     * Register a user with the provided registration request.
     *
     * @param request The registration request containing user details.
     * @return An authentication response with a message indicating the registration status.
     * @throws EmptyInputException If required input data is missing.
     */
    public AuthenticationResponse register(RegisterRequest request) throws EmptyInputException;
    /**
     * Authenticate a user with the provided authentication request.
     *
     * @param request The authentication request containing user credentials.
     * @return An authentication response with a token and a message indicating the authentication status.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    /**
     * Delete a user by their email address.
     *
     * @param email The email address of the user to be deleted.
     * @return A deletion request response with a message indicating the deletion status.
     */
    public DeletionRequest deleteUser(String email);
    /**
     * Soft deletes a user based on their unique identifier (ID).
     *
     * @param id The unique identifier (ID) of the user to be soft deleted.
     * @return A message indicating the result of the soft deletion process.
     * @throws NotFoundException If the user with the specified ID is not found.
     */
    public String softDeleteUser(Long id) throws NotFoundException;
    public ResponseEntity<String> forgotPassword(RegisterRequest registerRequest);

}

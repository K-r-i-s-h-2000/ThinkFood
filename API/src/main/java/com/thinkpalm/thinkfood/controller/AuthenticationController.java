package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.config.JwtService;
import com.thinkpalm.thinkfood.entity.User;
import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.AuthenticationRequest;
import com.thinkpalm.thinkfood.model.AuthenticationResponse;
import com.thinkpalm.thinkfood.model.DeletionRequest;
import com.thinkpalm.thinkfood.model.RegisterRequest;
import com.thinkpalm.thinkfood.repository.UserRepository;
import com.thinkpalm.thinkfood.service.AuthenticationService;
import com.thinkpalm.thinkfood.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Controller for handling authentication and user registration.
 * This controller provides endpoints for user registration, authentication, and user deletion.
 *
 * Author: ajay.S
 * Since: 31/10/2023
 * Version: 2.0
 */
@Log4j2
@RestController
@RequestMapping("/think-food/auth")
//@CrossOrigin("*")
@RequiredArgsConstructor
@SecurityRequirement(name="thinkfood")
public class AuthenticationController {

    @Autowired
    private  AuthenticationService service;
    @Autowired
    private LogoutHandler logoutHandler;
 @Autowired
 private UserRepository userRepository;

@Autowired
private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * Endpoint for user registration.
     * Registers a user based on the provided registration request.
     *
     * @param request The registration request.
     * @return A response entity with the authentication response.
     * @throws EmptyInputException If the registration request is empty.
     */
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        try {
            emailService.registerUser(request);
            return ResponseEntity.ok(service.register(request));
        }  catch (Exception e) {
            return ResponseEntity.ok("Failed to register. Please provide valid input.");
        }
    }
    @PostMapping("/forgot-password")
    public boolean forgotPassword(@RequestBody RegisterRequest request) {
        try {
            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

            if (optionalUser.isPresent()) {
                emailService.sendForgotPasswordEmail(request);
                return true;
            }
            else{
                return false;
            }
        }  catch (Exception e) {
            return ResponseEntity.ok("Failed.").hasBody();
        }
    }


    /**
     * Endpoint for user authentication.
     * Authenticates a user based on the provided authentication request.
     *
     * @param request The authentication request.
     * @return A response entity with the authentication response.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse>authenticate(
            @RequestBody AuthenticationRequest request
    )
    {
        return ResponseEntity.ok(service.authenticate(request));
    }
    /**
     * Endpoint for hard deleting a user.
     * Deletes a user based on the provided email.
     *
     * @param request A map containing the email of the user to be deleted.
     * @return The deletion request.
     */
    @DeleteMapping("/hard-delete")
    public DeletionRequest deleteUser(@RequestBody Map<String, String> request)  {
        String email = request.get("email");
        return service.deleteUser(email);
    }
    /**
     * Endpoint for soft deleting a customer.
     * Soft deletes a customer based on the provided ID.
     *
     * @param id The ID of the customer to be soft-deleted.
     * @return A response entity with the result of the soft delete operation.
     */
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDeleteCustomer(@PathVariable Long id) {
        try {
            String response = service.softDeleteUser(id);
            log.info("Delete for User with ID " + id + " was successful.");
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            log.error(" User not found for ID " + id + ": " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }



//    @PostMapping("/logout")
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        // Invoke the LogoutService to perform logout actions
//        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//    }


//    @PostMapping("/refresh-token")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        service.refreshToken(request, response);
//    }
}

package com.thinkpalm.thinkfood.service;


import com.thinkpalm.thinkfood.config.JwtService;
import com.thinkpalm.thinkfood.entity.*;
import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.AuthenticationRequest;
import com.thinkpalm.thinkfood.model.AuthenticationResponse;
import com.thinkpalm.thinkfood.model.DeletionRequest;
import com.thinkpalm.thinkfood.model.RegisterRequest;
import com.thinkpalm.thinkfood.repository.CustomerRepository;
import com.thinkpalm.thinkfood.repository.DeliveryRepository;
import com.thinkpalm.thinkfood.repository.RestaurantRepository;
import com.thinkpalm.thinkfood.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

/**
 * Service class that handles user authentication, registration, and token generation.
 * @author ajay.S
 * @version 2.0
 * @since 31/10/2023
 */
@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImplementation implements AuthenticationService {



    /**
     * Service for JWT token operations.
     */
    @Autowired
    private  JwtService jwtService;
    /**
     * Password encoder for securing user passwords.
     */
    @Autowired
    private  PasswordEncoder passwordEncoder;

    /**
     * Authentication manager for user authentication.
     */
    @Autowired
    private  AuthenticationManager authenticationManager;
    /**
     * Repository for customer data.
     */
    @Autowired
    private  CustomerRepository customerRepository;
    /**
     * Repository for restaurant data.
     */
    @Autowired
    private  RestaurantRepository restaurantRepository;
    /**
     * Repository for user data.
     */
    @Autowired
    private  UserRepository userRepository;
    /**
     * Repository for delivery data.
     */
    @Autowired
    private  DeliveryRepository deliveryRepository;
    /**
     * Service for restaurant operations.
     */
    @Autowired
    private  RestaurantService restaurantService;


    /**
     * Register a new user with the provided details.
     *
     * @param request The registration request containing user details.
     * @return An authentication response with a registration message.
     * @throws EmptyInputException If the input request is empty.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        try {
            log.info("Entered register method");
            int roleValue = request.getRole();
            Role userRole;
            if (roleValue == 1) {
                userRole = Role.CUSTOMER;
            } else if (roleValue == 2) {
                userRole = Role.RESTAURANT_OWNER;
            } else if (roleValue == 3) {
                userRole = Role.DELIVERY_AGENT;
            } else {
                // Handle the case where the role is not recognized
                throw new IllegalArgumentException("Invalid role value");
            }

            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

            if ((optionalUser.isPresent() &&  !optionalUser.get().getIsActive()) || optionalUser.isEmpty() )  {



                var user = User.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        // .role(Role.CUSTOMER)
                        .role(userRole)
                        .isActive(true)
//
                        .build();

                User savedUser = userRepository.save(user);


                if (roleValue == 1) {


                    Customer customer = Customer.builder()
                            .address(request.getCustomer().getCustomerAddress())
                            .longitude(request.getCustomer().getCustomerLongitude())
                            .latitude(request.getCustomer().getCustomerLatitude())
                            .dateOfBirth(request.getCustomer().getCustomerDateOfBirth())
                            .gender(request.getCustomer().getCustomerGender())
                            .customerName(request.getFirstname())
                            .email(request.getEmail())
                            .user(savedUser)
                            .isActive(true)
                            .build();

                    customerRepository.save(customer);
                } else if (roleValue == 2) {
                    Restaurant restaurant = Restaurant.builder()
                            .restaurantName(request.getRestaurant().getRestaurantName())
                            .restaurantDescription(request.getRestaurant().getRestaurantDescription())
                            .restaurantLatitude(request.getRestaurant().getRestaurantLatitude())
                            .restaurantLongitude(request.getRestaurant().getRestaurantLongitude())
                            .phoneNumber(request.getRestaurant().getPhoneNumber())
                            .email(request.getRestaurant().getEmail())
                            .restaurantAvailability(request.getRestaurant().getRestaurantAvailability())
                            .isActive(true)

                            .user(savedUser)
                            .build();
                    restaurantRepository.save(restaurant);


                } else if (roleValue == 3) {
                    Delivery delivery = Delivery.builder()
                            .deliveryName(request.getDelivery().getDeliveryName())
                            .deliveryContactNumber(request.getDelivery().getDeliveryContactNumber())
                            .deliveryAvailability(request.getDelivery().getDeliveryAvailability())
                            .vehicleNumber(request.getDelivery().getVehicleNumber())
                            .isActive(true)
                            .user(savedUser)
                            .build();

                    deliveryRepository.save(delivery);
                }

//
                var jwtToken = jwtService.generateToken(savedUser);
                return AuthenticationResponse.builder()
                        .message("Registration Completed Successfully")

                        .build();

            } else {
                // User already exists or is active
                return AuthenticationResponse.builder()
                        .message("User with the provided email already exists or is active.")
                        .build();
            }
        }
    catch (Exception e) {
            log.error("Error during registration: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Authenticate a user based on the provided email and password.
     *
     * @param request The authentication request containing email and password.
     * @return An authentication response with a generated token.
     */
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        try {
//            log.info("Received an authentication request");
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getEmail(),
//                            request.getPassword())
//            );
//
////            var user = userRepository.findByEmail(request.getEmail())
////                    .orElseThrow();
//            var user = userRepository.findByEmail(request.getEmail())
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
//
//            var jwtToken = jwtService.generateToken(user);
//            user.setAuthenticationToken(jwtToken);
//            userRepository.save(user);
//            return AuthenticationResponse.builder()
//                    .token(jwtToken)
//                    .message("Token is Generated")
//                    .build();
//        }catch (Exception e) {
//            log.error("Error during authentication: " + e.getMessage(), e);
//            throw e;
//        }
//    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            log.info("Received an authentication request");

            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

            if ((optionalUser.isPresent()) && (optionalUser.get().getIsActive())) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );

                User user = optionalUser.get();

                var jwtToken = jwtService.generateToken(user);
                user.setAuthenticationToken(jwtToken);
                userRepository.save(user);

                if(user.getRole()==Role.CUSTOMER) {
                    return AuthenticationResponse.builder()
                            .id(user.getId())
                            .token(jwtToken)
                            .message("Token is Generated")
                            .customerName(user.getCustomer().getCustomerName())
                            .role(user.getRole())
                            .custId(user.getCustomer().getId())
                            .customerAddress(user.getCustomer().getAddress())
                            .customerLatitude(String.valueOf(user.getCustomer().getLatitude()))
                            .customerLongitude(String.valueOf(user.getCustomer().getLongitude()))
                            .build();
                } else if (user.getRole() == Role.RESTAURANT_OWNER) {
                    return AuthenticationResponse.builder()
                            .token(jwtToken)
                            .message("Token is Generated")
                            .role(user.getRole())
                            .restaurantId(user.getRestaurant().getId())
                            .restaurantName(user.getRestaurant().getRestaurantName())
                            .restaurantDescription(user.getRestaurant().getRestaurantDescription())
                            .restaurantAvailability(user.getRestaurant().isRestaurantAvailability())
                            .build();
                }else if (user.getRole() == Role.ADMIN) {
                    return AuthenticationResponse.builder()
                            .token(jwtToken)
                            .message("Token is Generated")
                            .role(user.getRole())
                            .build();}

                    return AuthenticationResponse.builder()
                            .token(jwtToken)
                            .message("Token is Generated")
                            .role(user.getRole())
                            .deliveryId(user.getDelivery().getId())
                            .deliveryName(user.getDelivery().getDeliveryName())
                            .deliveryContactNumber(Integer.valueOf(user.getDelivery().getDeliveryContactNumber()))
                            .deliveryEmail(user.getDelivery().getDeliveryAvailability())
                            .deliveryVehicleNumber(user.getDelivery().getVehicleNumber())
                            .build();

            } else {
                log.warn("User not found with email: " + request.getEmail());
                // Handle the case where the user is not found, e.g., throw a custom exception or return an error message.
                throw new UsernameNotFoundException("User not found with email: " + request.getEmail());

            }
        } catch (Exception e) {
            log.error("Error during authentication: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Delete a user by their email.
     *
     * @param email The email of the user to be deleted.
     * @return A deletion request indicating the success or failure of the operation.
     */
    public DeletionRequest deleteUser(String email) {
        try {

            log.info("Deleting user with email: " + email);
            // Check if the order exists
            Optional<User> optionalOrder = userRepository.findByEmail(email);

            DeletionRequest response = new DeletionRequest();

            if (optionalOrder.isPresent()) {
                User user = optionalOrder.get();

                // Delete the order
                userRepository.delete(user);

                // Set response details for successful deletion
                response.setEmail(user.getEmail());
                response.setMessage("Customer deleted successfully");
            } else {
                // Set response details for order not found
                response.setEmail(email);
                response.setMessage("Customer not found");
            }

            return response;
        }catch (Exception e) {
            log.error("Error during user deletion: " + e.getMessage(), e);
            throw e;
        }
    }
    @Override
    public String softDeleteUser(Long id) throws NotFoundException {
        try {
            log.info("Entered softDeleteUser method");
            com.thinkpalm.thinkfood.entity.User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
            user.setIsActive(false);

            com.thinkpalm.thinkfood.entity.Customer customer = user.getCustomer();
            if (customer != null) {
                customer.setIsActive(false);
                // Save the updated customer
                customerRepository.save(customer);
            }

            // Save the updated user
            userRepository.save(user);


            return "User With Id "+id+" deleted successfully!";

        } catch (Exception e) {
            log.error("Error during softDeleteUser: " + e.getMessage(), e);
            throw e;
        }
    }


//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        userEmail = jwtService.extractUsername(refreshToken);
//        if (userEmail != null) {
//            var user = this.repository.findByEmail(userEmail)
//                    .orElseThrow();
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                var authResponse = AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//    }

//    private void revokeAllUserTokens(User user) {
//        var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
//    }

//    private void saveUserToken(User user, String jwtToken) {
//        var token = Token.builder()
//                .user(user)
//                .token(jwtToken)
//                .tokenType(TokenType.BEARER)
//                .expired(false)
//                .revoked(false)
//                .build();
//        tokenRepository.save(token);
//    }
@PostMapping("/forgot-password")
public ResponseEntity<String> forgotPassword(RegisterRequest registerRequest){
    try {
        // Search for the user by email in the repository
        Optional<User> optionalUser = userRepository.findByEmail(registerRequest.getEmail());



    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update password");
    }
    return null;
}

}



 
package com.thinkpalm.thinkfood.servicetest;

import com.thinkpalm.thinkfood.config.JwtService;
import com.thinkpalm.thinkfood.entity.Role;
import com.thinkpalm.thinkfood.entity.User;
import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.*;
import com.thinkpalm.thinkfood.repository.CustomerRepository;
import com.thinkpalm.thinkfood.repository.DeliveryRepository;
import com.thinkpalm.thinkfood.repository.RestaurantRepository;
import com.thinkpalm.thinkfood.repository.UserRepository;
import com.thinkpalm.thinkfood.service.AuthenticationServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthenticationServiceImplementationTest {


    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthenticationServiceImplementation authenticationService;


    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void testRegisterAsCustomer() throws EmptyInputException {
        RegisterRequest request = createSampleCustomerRegisterRequest();
        User savedUser = createSampleUser();
        savedUser.setIsActive(Boolean.TRUE);
        Customer customer=createSampleCustomer();

        when(userRepository.save(any(com.thinkpalm.thinkfood.entity.User.class))).thenReturn(savedUser);


// Mock behavior of CustomerRepository
        when(customerRepository.save(any(com.thinkpalm.thinkfood.entity.Customer.class))).thenReturn(savedUser.getCustomer());

        // Mock password encoding
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        // Test the register method
        AuthenticationResponse response = authenticationService.register(request);

        // Verify that the response is as expected
        Assertions.assertEquals("Registration Completed Successfully", response.getMessage());

    }



    @Test
    public void testRegisterAsRestaurantOwner() throws EmptyInputException {
        RegisterRequest request = createSampleRestaurantOwnerRegisterRequest();
        User savedUser = createSampleUser();
        Restaurant restaurant=createSampleRestaurant();

        // Mock behavior of UserRepository

        when(userRepository.save(Mockito.<User>any())).thenReturn(savedUser);


// Mock behavior of CustomerRepository
        when(restaurantRepository.save(Mockito.<com.thinkpalm.thinkfood.entity.Restaurant>any())).thenReturn(savedUser.getRestaurant());


        // Mock password encoding
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        // Test the register method
        AuthenticationResponse response = authenticationService.register(request);

        // Verify that the response is as expected
        Assertions.assertEquals("Registration Completed Successfully", response.getMessage());
    }

    @Test
    public void testRegisterAsDeliveryAgent() throws EmptyInputException {
        RegisterRequest request = createSampleDeliveryAgentRegisterRequest();
        User savedUser = createSampleUser();
        Delivery delivery = createSampleDelivery();

        // Mock behavior of UserRepository
        when(userRepository.save(any())).thenReturn(savedUser);

        // Mock behavior of DeliveryRepository
        when(deliveryRepository.save(any())).thenReturn(savedUser.getDelivery());

        // Mock password encoding
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        // Test the register method
        AuthenticationResponse response = authenticationService.register(request);

        // Verify that the response is as expected
        Assertions.assertEquals("Registration Completed Successfully", response.getMessage());
    }

    private RegisterRequest createSampleCustomerRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("johndoe@example.com");
        request.setPassword("password");
        request.setRole(1);
        //equest.setRole(Role.CUSTOMER.ordinal());
        request.setCustomer(createSampleCustomer());


        return request;
    }

    private Customer createSampleCustomer() {
        Customer customer = new Customer();
        customer.setCustomerName("John Doe");
        customer.setCustomerAddress("123 Main St");
        customer.setCustomerLongitude(12.345);
        customer.setCustomerLatitude(34.567);
        customer.setIsActive(true);
        // Set other customer properties
        return customer;
    }

    private RegisterRequest createSampleRestaurantOwnerRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("Jane");
        request.setLastname("Smith");
        request.setEmail("janesmith@example.com");
        request.setPassword("password");
        request.setRole(2);
        //  request.setRole(Role.RESTAURANT_OWNER);
        request.setRestaurant(createSampleRestaurant());
        return request;
    }

    private com.thinkpalm.thinkfood.model.Restaurant createSampleRestaurant() {
        com.thinkpalm.thinkfood.model.Restaurant restaurant = new com.thinkpalm.thinkfood.model.Restaurant();
        restaurant.setRestaurantName("Smith's Restaurant");
        restaurant.setRestaurantDescription("A great restaurant");
        restaurant.setLatitude(56.789);
        restaurant.setLongitude(78.901);
        restaurant.setRestaurantAvailability(Boolean.valueOf("open"));




        return restaurant;
    }

    private RegisterRequest createSampleDeliveryAgentRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("David");
        request.setLastname("Johnson");
        request.setEmail("davidjohnson@example.com");
        request.setPassword("password");
        request.setRole(3);

        //request.setRole(Role.DELIVERY_AGENT.ordinal());
        request.setDelivery(createSampleDelivery());
        return request;
    }

    private com.thinkpalm.thinkfood.model.Delivery createSampleDelivery() {
        com.thinkpalm.thinkfood.model.Delivery delivery = new com.thinkpalm.thinkfood.model.Delivery();
        delivery.setDeliveryName("David Johnson");
        delivery.setDeliveryContactNumber(12365);
//        delivery.setIs_active(true);
        // Set other delivery properties
        return delivery;
    }

    private User createSampleUser() {
        User user = new User();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.CUSTOMER);
        user.setIsActive(true);
        // Set other user properties
        return user;
    }


    @Test
    public void testAuthenticate_Failure() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("anjuss@gmail.com");
        request.setPassword("333");
        User user = new User();
        user.setEmail("anjuss@gmail.com");
        user.setPassword("$2a$10$odY4fRQB/aLyJOMApUMx8eLBYklbJSEKoU1rK0nU338T1tVdduoT"); // Replace with a hashed password
        when(userRepository.findByEmail("anjuss@gmail.com")).thenReturn(java.util.Optional.of(user));


        // Mock the authenticationManager to throw an exception for bad credentials
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act and assert
        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(request));


    }
    @Test
    public void testDeleteOrder_ExistingUser() {
        // Create a sample user and email
        User sampleUser = new User();
        sampleUser.setEmail("ansuu@gmail.com");

        String email = "ansuu@gmail.com";

        // Mock the UserRepository behavior to return the sample user when findByEmail is called
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(sampleUser));

        // Call the deleteOrder method
        DeletionRequest response = authenticationService.deleteUser(email);

        // Verify that the user was deleted
        verify(userRepository).delete(sampleUser);

        // Verify the response details for a successful deletion
        Assertions.assertEquals("ansuu@gmail.com", response.getEmail());
        Assertions.assertEquals("Customer deleted successfully", response.getMessage());
    }

    @Test
    public void testDeleteOrder_UserNotFound() {
        // Provide a non-existing email
        String email = "nonexistent@example.com";

        // Mock the UserRepository behavior to return an empty optional when findByEmail is called
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Call the deleteOrder method
        DeletionRequest response = authenticationService.deleteUser(email);

        // Verify that the user was not deleted
        verify(userRepository, never()).delete(Mockito.any());

        // Verify the response details for a user not found
        assertEquals("nonexistent@example.com", response.getEmail());
        assertEquals("Customer not found", response.getMessage());
    }
    @Test
    public void testSoftDeleteUser_Success() throws NotFoundException {
        // Arrange
        long userId =114L;


        User user = new User();
        user.setId(userId);
        user.setIsActive(true);


        com.thinkpalm.thinkfood.entity.Customer customer = new com.thinkpalm.thinkfood.entity.Customer();
        customer.setId((userId));
        customer.setIsActive(true);

        // Set the user's customer association
        user.setCustomer(customer);


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
       // when(customerRepository.findById(userId)).thenReturn(Optional.of(customer));

        // Act
        String result = authenticationService.softDeleteUser(userId);

        // Assert
        assertEquals("User With Id " + userId + " deleted successfully!", result);
        assertFalse(user.getIsActive());
       // assertFalse(customer.getIsActive());

        // Verify that the save methods were called
        verify(userRepository, times(1)).save(user);
      //  verify(customerRepository, times(1)).save(customer);
    }
    @Test
    public void testSoftDeleteCustomerById_CustomerNotFound() {
        // Arrange
        long userId = 34L;

        // Stub the repository method to return an empty Optional (customer not found)
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> authenticationService.softDeleteUser(userId));
    }

//    private User createUserWithCustomer() {
//        User user = new User();
//        user.setId(1L);
//        user.setIsActive(true);
//
//        com.thinkpalm.thinkfood.entity.Customer customer = new com.thinkpalm.thinkfood.entity.Customer();
//        customer.setIsActive(true);
//
//        user.setCustomer(customer);
//
//        return user;
//    }
@Test
void authenticate_SuccessfulAuthentication() {
    // Arrange
    AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
            .email("anjuss@gmail.com")
            .password("333")
            .build();

    User user = User.builder()
            .email("anjuss@gmail.com")
            .password("$10$odY4fRQB/aLyJOMApUMx8eLBYklbJSEKoU1rK0nU338T1tVdduoT.")
            .build();

    when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(user));
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(new UsernamePasswordAuthenticationToken(user, null));

    String mockedJwtToken = "mockedJwtToken";
    when(jwtService.generateToken(user)).thenReturn(mockedJwtToken);

    // Act
    AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

    // Assert
    assertEquals("Token is Generated", authenticationResponse.getMessage());
    assertEquals(mockedJwtToken, authenticationResponse.getToken());

    // Verify interactions
    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository, times(1)).save(user);
}@Test
    void authenticate_InvalidCredentials() {
        // Arrange
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("anjuss@gmail.com")
                .password("456212")
                .build();

        User user = User.builder()
                .email("anjuss@gmail.com")
                .password("hashedPassword")
                .build();

        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new UsernameNotFoundException("Invalid credentials"));

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(authenticationRequest));

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void authenticate_UserNotFound() {
        // Arrange
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("nonexistent@example.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(authenticationRequest));

        // Verify interactions
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).save(any(User.class));
    }
}
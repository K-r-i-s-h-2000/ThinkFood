package com.thinkpalm.thinkfood.servicetest;

import com.thinkpalm.thinkfood.entity.User;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.ChangePasswordRequest;
import com.thinkpalm.thinkfood.model.DeletionRequest;
import com.thinkpalm.thinkfood.repository.CustomerRepository;
import com.thinkpalm.thinkfood.repository.UserRepository;
import com.thinkpalm.thinkfood.service.UserServiceImplementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplementationTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UserServiceImplementation userServiceImplementation;
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);


    //}
    @Test
    public void changePassword_SuccessfulChange() {
        // Arrange
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("333");
        request.setNewPassword("555");
        request.setConfirmationPassword("555");

        User existingUser = new User("anjuss@gmail.com", "333", null);

        Principal principal = new UsernamePasswordAuthenticationToken(existingUser, null);

        when(passwordEncoder.matches(request.getCurrentPassword(), existingUser.getPassword())).thenReturn(true);

        // Act
        userServiceImplementation.changePassword(request, principal);

        // Assert
        verify(passwordEncoder, times(1)).matches(request.getCurrentPassword(), existingUser.getPassword());
        verify(passwordEncoder, times(1)).encode(request.getNewPassword());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void changePassword_WrongCurrentPassword() {
        // Arrange
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("555");
        request.setNewPassword("6666");
        request.setConfirmationPassword("6666");

        User existingUser = new User("anjuss@gmail.com", "555", null);

        Principal principal = new UsernamePasswordAuthenticationToken(existingUser, null);

        when(passwordEncoder.matches(request.getCurrentPassword(), existingUser.getPassword())).thenReturn(false);

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> userServiceImplementation.changePassword(request, principal));

        // Verify that save and encode methods are not called
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
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
        DeletionRequest response = userServiceImplementation.deleteUser(email);

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
        DeletionRequest response = userServiceImplementation.deleteUser(email);

        // Verify that the user was not deleted
        verify(userRepository, Mockito.never()).delete(Mockito.any());

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
        String result = String.valueOf(userServiceImplementation.softDeleteUser(userId));

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
        assertThrows(NotFoundException.class, () -> userServiceImplementation.softDeleteUser(userId));
    }
}

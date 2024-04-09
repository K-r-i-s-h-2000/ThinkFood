package com.thinkpalm.thinkfood.servicetest;

import com.thinkpalm.thinkfood.config.JwtService;
import com.thinkpalm.thinkfood.entity.Customer;
import com.thinkpalm.thinkfood.exception.CustomerNotFoundException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.repository.CustomerRepository;
import com.thinkpalm.thinkfood.repository.UserRepository;
import com.thinkpalm.thinkfood.service.AuthenticationServiceImplementation;
import com.thinkpalm.thinkfood.service.CustomerServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceImplementationTest {

    @InjectMocks private CustomerServiceImplementation customerService;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailsService userDetailsService;
    private AuthenticationServiceImplementation authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void testFindCustomerDetailsWhenCustomerExists() throws NotFoundException, ParseException {
        // Arrange
        long customerId = 55L;

        // Create a sample customer entity
        Customer customerEntity = new Customer();
        customerEntity.setId(55L);
        customerEntity.setCustomerName("sreekumar");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = dateFormat.parse("1998-01-15");
        customerEntity.setDateOfBirth(dateOfBirth);

        customerEntity.setLongitude(123.456);
        customerEntity.setLatitude(78.91);
        customerEntity.setAddress("123 Main St");
        customerEntity.setGender("male");
        customerEntity.setEmail("sreekumar@gmail.com");
        customerEntity.setIsActive(true);
// Create a non-parameterized Optional
        Optional nonParameterizedOptional = Optional.of(customerEntity);

// Stub the repository method to return the non-parameterized Optional
        when(customerRepository.findById(customerId)).thenReturn(nonParameterizedOptional);

        // Stub the repository method to return the customer entity when findById is called with 1L.

        // Act
        com.thinkpalm.thinkfood.model.Customer result = customerService.findCustomerDetails(55L);

        // Assert
        assertEquals(String.valueOf(customerEntity.getId()), result.getId());
        assertEquals(customerEntity.getCustomerName(), result.getCustomerName());
        assertEquals(customerEntity.getDateOfBirth(), result.getCustomerDateOfBirth());
        assertEquals(customerEntity.getLongitude(), result.getCustomerLongitude());
        assertEquals(customerEntity.getLatitude(), result.getCustomerLatitude());
        assertEquals(customerEntity.getAddress(), result.getCustomerAddress());
        assertEquals(customerEntity.getGender(), result.getCustomerGender());
        assertEquals(customerEntity.getEmail(), result.getEmail());
    }


    @Test
    public void testFindCustomerDetailsWhenCustomerDoesNotExist() {
        // Arrange
        long customerId = 34L;
        Customer customerEntity = new Customer();
        customerEntity.setId(34L);
        customerEntity.setCustomerName("krishna");
        customerEntity.setIsActive(false);

        // Stub the repository method to return an empty optional, indicating that the customer does not exist.
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act and Assert
        try {
            customerService.findCustomerDetails(customerId);
        } catch (NotFoundException e) {
            // Verify that NotFoundException is thrown with the correct message.
            assertEquals("Customer not found with"+customerId, e.getMessage()); // Note the space after "with"
        }
    }



    @Test
    public void testUpdateCustomer_Success() throws CustomerNotFoundException {
        // Create a sample customer entity
        com.thinkpalm.thinkfood.model.Customer updatedCustomer = new com.thinkpalm.thinkfood.model.Customer();
        updatedCustomer.setCustomerAddress("Vernacular");
        updatedCustomer.setCustomerLatitude(123.25);
        updatedCustomer.setCustomerGender("Male");
        updatedCustomer.setCustomerLongitude(456.36);
        updatedCustomer.setCustomerName("Daan");
        updatedCustomer.setEmail("dann123@gmail.com");


        // Set other properties as needed

        Customer existingCustomer = new Customer();
        existingCustomer.setId(55L);
        existingCustomer.setIsActive(true);
        // Set other properties as needed

        when(customerRepository.findById(55L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(com.thinkpalm.thinkfood.entity.Customer.class))).thenReturn(existingCustomer);

        com.thinkpalm.thinkfood.model.Customer result = customerService.updateCustomer(55L, updatedCustomer);

        assertNotNull(result);
        assertEquals("Vernacular", result.getCustomerAddress());
        assertEquals(123.25, result.getCustomerLatitude());
        assertEquals(456.36, result.getCustomerLongitude());
        assertEquals("Male", result.getCustomerGender());
        assertEquals("dann123@gmail.com", result.getEmail());
        assertEquals("Daan", result.getCustomerName());


        // Assert other properties as needed
    }

    @Test
    public void testUpdateCustomer_CustomerNotFound() {
        // Create a sample customer entity
        com.thinkpalm.thinkfood.model.Customer updatedCustomer = new com.thinkpalm.thinkfood.model.Customer();
        updatedCustomer.setCustomerAddress("Vernacular");
        updatedCustomer.setCustomerLatitude(123.25);
        updatedCustomer.setCustomerGender("Male");
        updatedCustomer.setCustomerLongitude(456.36);
        updatedCustomer.setCustomerName("Daan");
        updatedCustomer.setEmail("dann123@gmail.com");


        // Mock the repository to return an empty optional, simulating a customer not found
        when(customerRepository.findById(34L)).thenReturn(Optional.empty());

        // Ensure that the method throws a CustomerNotFoundException
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(34L, updatedCustomer);
        });
    }

    @Test
    void testGetAllCustomers() {
        // Create a sample list of customers
        List<Customer> sampleCustomers = new ArrayList<>();
        for (long i = 1; i <= 5; i++) {
            com.thinkpalm.thinkfood.entity.Customer customer = new com.thinkpalm.thinkfood.entity.Customer();
            customer.setId(i);
            customer.setCustomerName("Customer " + i);
            customer.setLatitude(40.0 + i);
            customer.setLongitude(-75.0 - i);
            customer.setGender("Male");
            customer.setEmail("customer" + i + "@example.com");
            customer.setAddress("Address " + i);
            sampleCustomers.add(customer);
        }

        // Create a sample Page of customers
        Page<Customer> samplePage = new PageImpl<>(sampleCustomers);

        // Stub the repository method to return the sample Page when findAll is called with any PageRequest
        Mockito.when(customerRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(samplePage);

        // Call the service method
        List<com.thinkpalm.thinkfood.model.Customer> result = customerService.getAllCustomers(0, 10);

        // Assertions
        assertEquals(5, result.size());  // Check the number of customers returned

        // You can add more specific assertions here to check the content of the returned customers.
        // For example:
        assertEquals("Customer 1", result.get(0).getCustomerName());
        assertEquals("customer1@example.com", result.get(0).getEmail());
    }



    //}
    @Test
    public void testDeleteCustomerById() throws NotFoundException {
        // Create a sample customer entity
        com.thinkpalm.thinkfood.entity.Customer customerEntity = new com.thinkpalm.thinkfood.entity.Customer();
        customerEntity.setId(34L); // Set the ID here if it's used in mapping

        // Mock the behavior of customerRepository.findById
        when(customerRepository.findById(34L)).thenReturn(Optional.of(customerEntity));

        // Mock the behavior of userRepository.delete
        doNothing().when(userRepository).delete(any());

        // Create a Customer model object and set the expected ID
        com.thinkpalm.thinkfood.model.Customer deletedCustomerModel = new com.thinkpalm.thinkfood.model.Customer();
        deletedCustomerModel.setId("34"); // Set the expected ID here

        // Invoke the method to be tested
        com.thinkpalm.thinkfood.model.Customer deletedCustomer = customerService.deleteCustomerById(34L);

        // Assert that the customer was deleted successfully
        assertNotNull(deletedCustomer);

        // Compare the expected ID with the actual ID in the model
        assertEquals(deletedCustomerModel.getId(), deletedCustomer.getId());

        // Additional assertions as needed
    }
    @Test
    public void testDeleteCustomerByIdFailure() {
        // Mock the behavior of customerRepository.findById to return an empty Optional
        when(customerRepository.findById(34L)).thenReturn(Optional.empty());

        // Invoke the method to be tested and expect a NotFoundException
        assertThrows(NotFoundException.class, () -> {
            customerService.deleteCustomerById(34L);
        });
    }

    @Test
    public void testSoftDeleteCustomerById_Success() throws NotFoundException {
        // Arrange
        long customerId = 55L;

        // Create a sample customer entity
        com.thinkpalm.thinkfood.entity.Customer customer = new com.thinkpalm.thinkfood.entity.Customer();
        customer.setId(customerId);
        customer.setIsActive(true);

        // Create a sample user entity
        com.thinkpalm.thinkfood.entity.User user = new com.thinkpalm.thinkfood.entity.User();
        user.setId(55L);
        user.setIsActive(true);

        // Associate the user with the customer
        customer.setUser(user);

        // Stub the repository methods to return the customer and user entities
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        String result = customerService.softDeleteCustomerById(customerId);

        // Assert
        assertEquals("Customer With Id " + customerId + " deleted successfully!", result);
        assertFalse(customer.getIsActive());
        assertFalse(user.getIsActive());

        // Verify that the save methods were called
        verify(customerRepository, times(1)).save(customer);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testSoftDeleteCustomerById_CustomerNotFound() {
        // Arrange
        long customerId = 34L;

        // Stub the repository method to return an empty Optional (customer not found)
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> customerService.softDeleteCustomerById(customerId));
    }


}

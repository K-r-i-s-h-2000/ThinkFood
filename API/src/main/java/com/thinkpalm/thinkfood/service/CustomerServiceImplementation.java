package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.User;
import com.thinkpalm.thinkfood.exception.CustomerNotFoundException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.Customer;
import com.thinkpalm.thinkfood.repository.CustomerRepository;
import com.thinkpalm.thinkfood.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class that handles operations related to customer data.
 * @author ajay.S
 * @version 2.0
 * @since 31/10/2023
 */
@Slf4j

@Service
public class CustomerServiceImplementation  implements CustomerService {
    /**
     * Repository for customer data.
     */
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Repository for user data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Update the details of a customer with the given ID.
     *
     * @param id              The ID of the customer to update.
     * @param updatedCustomer The updated customer details.
     * @return The updated customer model.
     * @throws CustomerNotFoundException If the customer with the specified ID is not found.
     */
    @Override
    public Customer updateCustomer(Long id, Customer updatedCustomer) throws CustomerNotFoundException {
        com.thinkpalm.thinkfood.entity.Customer customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("   CUSTOMER not found with ID:" + id));
        if (!customerEntity.getIsActive()) {
            try {
                log.info("Entered updateCustomer method");
                throw new NotFoundException("Customer  with id " + id + " is not found");
            } catch (NotFoundException e) {
                log.error("Error during softDeleteCustomerById: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }


        if (updatedCustomer.getCustomerAddress() != null) {
            customerEntity.setAddress(updatedCustomer.getCustomerAddress());
        }

        if (updatedCustomer.getCustomerLongitude() != null) {
            customerEntity.setLongitude(updatedCustomer.getCustomerLongitude());
        }

        if (updatedCustomer.getCustomerLatitude() != null) {
            customerEntity.setLatitude(updatedCustomer.getCustomerLatitude());
        }

        if (updatedCustomer.getCustomerGender() != null) {
            customerEntity.setGender(updatedCustomer.getCustomerGender());
        }

        if (updatedCustomer.getCustomerDateOfBirth() != null) {
            customerEntity.setDateOfBirth(updatedCustomer.getCustomerDateOfBirth());
        }
        if (updatedCustomer.getCustomerName() != null) {
            customerEntity.setCustomerName(updatedCustomer.getCustomerName());

        }
        if (updatedCustomer.getEmail() != null) {
            customerEntity.setEmail(updatedCustomer.getEmail());
        }


        customerEntity = customerRepository.save(customerEntity);

        Customer customerModel = new Customer();
        customerModel.setId(customerModel.getId());
        customerModel.setCustomerAddress(customerEntity.getAddress());
        customerModel.setCustomerGender(customerEntity.getGender());
        customerModel.setCustomerLatitude(customerEntity.getLatitude());
        customerModel.setCustomerLongitude(customerEntity.getLongitude());
        customerModel.setCustomerDateOfBirth(customerEntity.getDateOfBirth());
        customerModel.setCustomerName(customerEntity.getCustomerName());
        customerModel.setEmail(customerEntity.getEmail());
        return customerModel;

    }

    /**
     * Retrieve details of a customer by their ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return The customer model with the specified ID.
     * @throws NotFoundException If the customer with the specified ID is not found.
     */
    @Override
    public Customer findCustomerDetails(Long customerId) throws NotFoundException {
        log.info("Entered findCustomerDetails method");
        try {
            Optional<com.thinkpalm.thinkfood.entity.Customer> optionalCustomer = customerRepository.findById(customerId);

            if (optionalCustomer.isPresent()) {
                com.thinkpalm.thinkfood.entity.Customer customer = optionalCustomer.get();
                if (!customer.getIsActive()) {
                    throw new NotFoundException("Customer  with id " + customerId + " is not found");
                }
                Customer customer1 = new Customer();
                customer1.setId(String.valueOf(customer.getId()));
                customer1.setCustomerDateOfBirth(customer.getDateOfBirth());
                customer1.setCustomerLongitude(customer.getLongitude());
                customer1.setCustomerAddress(customer.getAddress());
                customer1.setCustomerLatitude(customer.getLatitude());
                customer1.setCustomerGender(customer.getGender());
                customer1.setEmail(customer.getEmail());
                customer1.setCustomerName(customer.getCustomerName());
                customer1.setImage(customer.getImage());
                log.info("Retrieved customer details successfully");
                return customer1;
            } else {

                log.error("Customer not found with id " + customerId);
                throw new NotFoundException("Customer not found with" + customerId);

            }
        } catch (Exception e) {
            log.error("Error during findCustomerDetails: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get a list of customers with pagination.
     *
     * @param pageNo   The page number.
     * @param pageSize The number of customers per page.
     * @return A list of customer models.
     */
    @Override
    public List<Customer> getAllCustomers(Integer pageNo, Integer pageSize) {
        log.info("Entered getAllCustomers method");
        try {
            PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        Page<com.thinkpalm.thinkfood.entity.Customer> customerEntity = customerRepository.findAll(pageRequest);
        List<Customer> customerList = new ArrayList<>();
        customerEntity.forEach(customer -> {
            Customer customerModel = new Customer();
            customerModel.setId(String.valueOf(customer.getId()));
            customerModel.setCustomerName(customer.getCustomerName());
            customerModel.setCustomerLatitude(customer.getLatitude());
            customerModel.setCustomerLongitude(customer.getLongitude());
            customerModel.setCustomerGender(customer.getGender());
            customerModel.setEmail(customer.getEmail());
            customerModel.setCustomerAddress(customer.getAddress());
            customerList.add(customerModel);
        });
        log.info("Retrieved customer data successfully");
        return customerList;
    }

catch (Exception e) {
        log.error("Error during getAllCustomers: " + e.getMessage(), e);
        throw e;
        }
    }



    /**
     * Delete a customer by their ID, including associated user data if present.
     *
     * @param id The ID of the customer to delete.
     * @return The customer model of the deleted customer.
     * @throws NotFoundException If the customer with the specified ID is not found.
     */
    @Override
    public Customer deleteCustomerById(Long id) throws NotFoundException {

        Optional<com.thinkpalm.thinkfood.entity.Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            throw new NotFoundException("customer not found with ID: " + id);
        }

        com.thinkpalm.thinkfood.entity.Customer deletedCustomer = customerOptional.get();


        User associatedUser = deletedCustomer.getUser();

        if (associatedUser != null) {
            userRepository.delete(associatedUser);
        }



        customerRepository.deleteById(id);
        Customer deletedCustomerModel = new Customer();
        deletedCustomerModel.setId(String.valueOf(deletedCustomer.getId()));
        deletedCustomerModel.setCustomerName(deletedCustomer.getCustomerName());
        deletedCustomerModel.setCustomerLatitude(deletedCustomer.getLatitude());
        deletedCustomerModel.setCustomerLongitude(deletedCustomer.getLongitude());
        deletedCustomerModel.setCustomerGender(deletedCustomer.getGender());
        deletedCustomerModel.setEmail(deletedCustomer.getEmail());
        deletedCustomerModel.setCustomerAddress(deletedCustomer.getAddress());

        return deletedCustomerModel;
    }
    /**
     * Soft deletes a customer and their associated user by the specified unique identifier (ID).
     *
     * @param id The unique identifier (ID) of the customer to be soft deleted.
     * @return A message indicating the successful soft deletion of the customer and their associated user.
     * @throws NotFoundException If the customer with the specified ID is not found in the database.
     */
    @Override
    public String softDeleteCustomerById(Long id) throws NotFoundException {
        try {
            log.info("Entered softDeleteCustomerById method");
            com.thinkpalm.thinkfood.entity.Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer with ID " + id + " not found"));
            customer.setIsActive(false);


            customerRepository.save(customer);
            Long userId = customer.getUser().getId();
            com.thinkpalm.thinkfood.entity.User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Customer with ID " + id + " not found"));

            user.setIsActive(false);
            userRepository.save(user);


            return "Customer With Id "+id+" deleted successfully!";

        } catch (Exception e) {
            log.error("Error during softDeleteCustomerById: " + e.getMessage(), e);
            throw e;
        }
    }



}
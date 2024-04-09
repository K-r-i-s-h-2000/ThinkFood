package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.CustomerNotFoundException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.Customer;
import com.thinkpalm.thinkfood.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling customer-related operations.
 * This controller provides endpoints for updating, retrieving, and deleting customer information.
 *
 * Author: ajay.s
 * Since: 31/10/2023
 * Version: 2.0
 */
@Log4j2
@RestController
@RequestMapping("/think-food/customer")
@SecurityRequirement(name="thinkfood")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    /**
     * Endpoint for updating customer details.
     * Updates the details of a customer with the specified ID.
     *
     * @param id            The ID of the customer to be updated.
     * @param updateCustomer The updated customer details.
     * @return A message indicating the update status.
     */
    @PutMapping("update/{id}")
    public boolean updateCustomer(@PathVariable Long id, @RequestBody Customer updateCustomer) {
        try {
            Customer updated = customerService.updateCustomer(id, updateCustomer);
            return true;
        } catch (CustomerNotFoundException e) {
            return false;
        }

    }
    /**
     * Endpoint for retrieving customer details by ID.
     * Retrieves the details of a customer with the specified ID.
     *
     * @param customerId The ID of the customer to be retrieved.
     * @return The customer's details.
     * @throws NotFoundException If the customer is not found.
     */
    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable Long customerId) throws NotFoundException {
        Customer customer=customerService.findCustomerDetails(customerId);
        if(customer==null){
            throw new NotFoundException("Customer not found with"+customerId);
        }
        return customer;
    }
    /**
     * Endpoint for retrieving all customers with pagination.
     * Retrieves a list of customers with optional pagination parameters.
     *
     * @param pageNo   The page number.
     * @param pageSize The page size.
     * @return A list of customers.
     */
    @GetMapping("/get-all")
    public List<Customer> getAllCustomers(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize)
    {
        return customerService.getAllCustomers(pageNo,pageSize);
    }
    /**
     * Endpoint for hard deleting a customer by ID.
     * Deletes a customer with the specified ID.
     *
     * @param id The ID of the customer to be hard-deleted.
     * @return A message indicating the delete status.
     */
    @DeleteMapping("/hard-delete/{id}")
    public String deleteCustomerById(@PathVariable Long id) {
        try {
            customerService.deleteCustomerById(id);
            log.info("Delete for Customer with ID " + id + " was successful.");
            return "Customer with ID " + id + " has been deleted.";
        } catch (NotFoundException e) {
            log.error(" Customer not found for ID " + id + ": " + e.getMessage());
            return "Invalid Customer Id";
        }
    }
    /**
     * Endpoint for soft deleting a customer by ID.
     * Soft deletes a customer with the specified ID.
     *
     * @param id The ID of the customer to be soft-deleted.
     * @return A response entity with the result of the soft delete operation.
     */
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDeleteCustomer(@PathVariable Long id) {
        try {
            String response = customerService.softDeleteCustomerById(id);
            log.info("Delete for Customer with ID " + id + " was successful.");
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            log.error(" Customer not found for ID " + id + ": " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }



}

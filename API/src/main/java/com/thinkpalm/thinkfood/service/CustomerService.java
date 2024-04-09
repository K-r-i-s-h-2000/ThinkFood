package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.CustomerNotFoundException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.Customer;

import java.util.List;

/**
 * Service interface for managing customer data.
 * @author ajay.S
 * @version 2.0
 * @since 31/10/2023
 */
public interface CustomerService   {
    /**
     * Update the details of a customer with the given ID.
     *
     * @param id             The ID of the customer to update.
     * @param updatedCustomer The updated customer details.
     * @return The updated customer model.
     * @throws CustomerNotFoundException If the customer with the specified ID is not found.
     */
    public Customer updateCustomer(Long id, Customer updatedCustomer) throws CustomerNotFoundException;
    /**
     * Retrieve details of a customer by their ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return The customer model with the specified ID.
     * @throws NotFoundException If the customer with the specified ID is not found.
     */
    public Customer findCustomerDetails(Long customerId) throws NotFoundException;
    /**
     * Get a list of customers with pagination.
     *
     * @param pageNo    The page number.
     * @param pageSize  The number of customers per page.
     * @return A list of customer models.
     */
    public List<Customer> getAllCustomers(Integer pageNo, Integer pageSize);
    /**
     * Delete a customer by their ID.
     *
     * @param id The ID of the customer to delete.
     * @return The customer model of the deleted customer.
     * @throws NotFoundException If the customer with the specified ID is not found.
     */
    public Customer deleteCustomerById(Long id) throws NotFoundException;
    /**
     * Soft deletes a customer by the specified unique identifier (ID).
     *
     * @param id The unique identifier (ID) of the customer to be soft deleted.
     * @return A message indicating the successful soft deletion of the customer.
     * @throws NotFoundException If the customer with the specified ID is not found in the database.
     */
    public String softDeleteCustomerById(Long id) throws NotFoundException;
}
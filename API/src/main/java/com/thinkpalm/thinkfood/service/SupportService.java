/**
 * Service interface for managing support queries.
 * This interface defines the contract for performing operations related to support queries.
 *
 * @author agrah.mv
 * @since 31 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.InactiveSupportException;
import com.thinkpalm.thinkfood.exception.SupportNotFoundException;
import com.thinkpalm.thinkfood.model.Support;
import com.thinkpalm.thinkfood.model.Support1;

import java.util.Map;

public interface SupportService {

    /**
     * Creates a new support query.
     *
     * @param support The Support object containing the details of the support query.
     * @return The created Support object.
     * @throws EmptyInputException If the input Support object is empty or null.
     */
    Support createSupport(Support support) throws EmptyInputException;

    /**
     * Retrieves a map of all support queries with their corresponding IDs.
     *
     * @return A map where the key is the support query ID and the value is the query itself.
     */
    Map<Long, String> getAllQueries();

    /**
     * Retrieves a support query by its ID.
     *
     * @param id The ID of the support query to retrieve.
     * @return The Support1 object representing the support query.
     * @throws SupportNotFoundException If the support query with the given ID is not found.
     * @throws InactiveSupportException If the support query with the given ID is inactive.
     */
    Support1 getSupportById(Long id) throws SupportNotFoundException, InactiveSupportException;

    /**
     * Deletes a support query by its ID.
     *
     * @param id The ID of the support query to delete.
     * @return The Support object representing the deleted support query.
     * @throws SupportNotFoundException If the support query with the given ID is not found.
     */
    Support deleteSupportById(Long id) throws SupportNotFoundException;

    /**
     * Soft deletes a support query by marking it as inactive.
     *
     * @param id The ID of the support query to soft delete.
     * @return A message indicating the success of the soft deletion.
     * @throws SupportNotFoundException If the support query with the given ID is not found.
     */
    String softDeleteSupportById(Long id) throws SupportNotFoundException;
}

/**
 * Service interface for managing restaurants.
 * This interface defines the contract for performing operations related to restaurants.
 * author: Sharon Sam
 * since : 26 October 2023
 * version : 2.0
 */

package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.exception.RestaurantNotFoundException;
import com.thinkpalm.thinkfood.model.Restaurant;

public interface RestaurantService {

    /**
     * Get a restaurant by its ID.
     *
     * @param id The ID of the restaurant to retrieve.
     * @return The restaurant with the specified ID.
     * @throws NotFoundException If the restaurant with the given ID is not found.
     */

    Restaurant getRestaurantById(Long id) throws NotFoundException;

    /**
     * Update the preparation status for an order.
     *
     * @param orderId The ID of the order to update.
     * @return A message indicating the result of the update.
     * @throws NotFoundException If the order with the given ID is not found.
     */

    String updatePreparationStatus(Long orderId) throws NotFoundException;

    /**
     * Create a new restaurant.
     *
     * @param restaurant The restaurant to create.
     * @return The created restaurant.
     * @throws EmptyInputException If any required field is empty in the input.
     */

    Restaurant createRestaurant(Restaurant restaurant) throws EmptyInputException;

    /**
     * Update an existing restaurant.
     *
     * @param id The ID of the restaurant to update.
     * @param updatedRestaurant The updated restaurant data.
     * @return The updated restaurant.
     * @throws NotFoundException If the restaurant with the given ID is not found.
     */

    Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) throws NotFoundException;

    /**
     * Hard delete a restaurant by its ID.
     *
     * @param id The ID of the restaurant to delete.
     * @return The deleted restaurant.
     * @throws NotFoundException If the restaurant with the given ID is not found.
     */

    Restaurant deleteRestaurantById(Long id) throws NotFoundException;

    /**
     * Soft delete a restaurant by its ID.
     *
     * @param id The ID of the restaurant to delete.
     * @return The deleted restaurant.
     * @throws NotFoundException If the restaurant with the given ID is not found.
     */

    Boolean softDeleteRestaurantById(Long id) throws NotFoundException;

}

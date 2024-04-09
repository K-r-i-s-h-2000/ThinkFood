/**
 * Controller for managing restaurant operations.
 *
 * @author Sharon Sam
 * @since 26 October 2023
 * @version 2.0
 */


package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.Restaurant;
import com.thinkpalm.thinkfood.service.EmailService;
import com.thinkpalm.thinkfood.service.RestaurantService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/think-food/restaurant")
@Log4j2
@SecurityRequirement(name="thinkfood")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private EmailService emailService;


    /**
     * Get restaurant by ID.
     *
     * @param id The ID of the restaurant to retrieve.
     * @return A response entity containing the restaurant details.
     */

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRestaurantById(@PathVariable Long id) {
        try {
            log.info("Fetching restaurant by ID: {}", id);

            Restaurant restaurant = restaurantService.getRestaurantById(id);
            log.info("Retrieved restaurant values");

            return ResponseEntity.ok(restaurant);
        } catch (NotFoundException ex) {
            log.error("Restaurant not found for ID: {}", id);
            return ResponseEntity.ok("Restaurant not found for ID:"+id);
        }
    }


    @GetMapping("/update-preparation-status/{orderId}")
    public Boolean updatePreparationStatus(@PathVariable Long orderId) throws NotFoundException {
        try {
            log.info("Updating preparation status for order ID: {}", orderId);
            emailService.updateStatusEmail(orderId);
            log.info("Preparation status updated for order ID: {}", orderId);
            return true;

        }catch (NotFoundException e){
            log.error("Order ID not found: {}", orderId, e);
            return false;
        }

    }

    /**
     * Create a new restaurant.
     *
     * @param restaurant The menu details to create.
     * @return A response entity indicating the success or failure of the operation.
     */

    @PostMapping("/create")
    public String createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            log.info("Creating restaurant");
            restaurantService.createRestaurant(restaurant);
            log.info("Restaurant created successfully");
            return "Restaurant created successfully";
        } catch (EmptyInputException e) {
            log.error("Failed to create restaurant: Empty input or missing required fields", e);
            return "Input is empty or missing required fields";
        }
    }

    /**
     * Update a restaurant with the specified ID.
     *
     * @param id          The ID of the restaurant to update.
     * @param updatedRestaurant The updated restaurant details.
     * @return A ResponseEntity indicating the success or failure of the update operation.
     */


    @PutMapping("/update/{id}")
    public Object updateRestaurant(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
        try {
            log.info("Updating restaurant with ID: {}", id);
            Restaurant updated = restaurantService.updateRestaurant(id, updatedRestaurant);
            log.info("Restaurant updated successfully");
            return updatedRestaurant;
        } catch (NotFoundException e) {
            log.error("Invalid Restaurant ID: {}", id, e);
            return false;
        }
    }

    /**
     * Delete a restaurant with the specified ID.
     *
     * @param id The ID of the restaurant to be deleted.
     * @return A ResponseEntity indicating the success or failure of the delete operation.
     */

    @DeleteMapping("/delete/{id}")
    public String deleteRestaurantById(@PathVariable Long id) {
        try {
            log.info("Deleting restaurant with ID: {}", id);
            restaurantService.deleteRestaurantById(id);
            log.info("Restaurant with ID {} has been deleted.", id);
            return "Restaurant with ID " + id + " has been deleted.";
        } catch (NotFoundException e) {
            log.error("Invalid Restaurant ID: {}", id, e);
            return "Invalid Restaurant Id";
        }
    }

    /**
     * Soft delete a restaurant with the specified ID.
     *
     * @param id The ID of the restaurant to be soft-deleted.
     * @return A ResponseEntity indicating the success or failure of the operation.
     */

    @DeleteMapping("/{id}")
    public Boolean softDeleteRestaurant(@PathVariable Long id)  {
        log.info("Received request to delete restaurant with ID: {}", id);
        try {
            restaurantService.softDeleteRestaurantById(id);
            log.info("Restaurant with ID {} deleted successfully!", id);
            return true;
        } catch (NotFoundException e) {
            log.error("Invalid Restaurant ID: {}", id, e);
            return false;
        }

    }

}

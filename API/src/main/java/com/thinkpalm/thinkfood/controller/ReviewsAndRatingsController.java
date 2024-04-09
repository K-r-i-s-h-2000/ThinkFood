/**
 * Controller for managing reviews and ratings operations.
 *
 * This controller handles various operations related to reviews and ratings, including creating,
 * retrieving, updating, and deleting reviews and ratings. It provides methods for both regular and
 * soft deletion of reviews and ratings.
 *
 * @author agrah.mv
 * @since 30 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.*;
import com.thinkpalm.thinkfood.model.ReviewsAndRatings;
import com.thinkpalm.thinkfood.service.ReviewsAndRatingsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@SecurityRequirement(name="thinkfood")
@RestController
@RequestMapping("/think-food/reviews-ratings")
public class ReviewsAndRatingsController {

    @Autowired
    private ReviewsAndRatingsService reviewsAndRatingsService;


    /**
     * Create reviews and ratings.
     *
     * @param reviewsAndRatings The reviews and ratings details to create.
     * @return A message indicating the success or failure of the create operation.
     */
    @PostMapping("/create")
    public Boolean createReviewAndRatings(@RequestBody ReviewsAndRatings reviewsAndRatings){
        try {
            log.info("Entered into create reviews and ratings method");
            reviewsAndRatingsService.createReviewsAndRatings(reviewsAndRatings);
            return true;
        }catch(EmptyInputException e){
            log.error("enter all fields");
            return false;
        }catch(RestaurantNotFoundException e){
            log.error("Restaurant not found with ID");
            return false;
        }catch(CustomerNotFoundException e){
            log.error("Customer not found with ID");
            return false;
        }catch(InvalidRatingException  e){
            log.error("Rating should be between 1 and 5");
            return false;
        }
    }

    /**
     * Get reviews and ratings by ID.
     *
     * @param id The ID of the reviews and ratings to retrieve.
     * @return A response entity containing the reviews and ratings details.
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getReviewsAndRatingsById(@PathVariable Long id) {
        try {
            log.info("Entered into getReviewsAndRatingsById method");
            ReviewsAndRatings reviewsAndRatings = reviewsAndRatingsService.getReviewsAndRatingsById(id);
            return ResponseEntity.ok(reviewsAndRatings);
        } catch (ReviewsAndRatingsNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Update reviews and ratings by ID.
     *
     * @param id                    The ID of the reviews and ratings to update.
     * @param updatedReviewsAndRatings The updated reviews and ratings details.
     * @return A response entity containing the updated reviews and ratings details.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateReviewsAndRatingsById(@PathVariable Long id, @RequestBody ReviewsAndRatings updatedReviewsAndRatings) {
        try {
            log.info("Entered into updateReviewsAndRatingsById method");
            ReviewsAndRatings updateReviewsAndRatings = reviewsAndRatingsService.updateReviewsAndRatingsById(id, updatedReviewsAndRatings);
            return ResponseEntity.ok(updateReviewsAndRatings);
        }catch(ReviewsAndRatingsNotFoundException e){
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Delete reviews and ratings by ID.
     *
     * @param id The ID of the reviews and ratings to be deleted.
     * @return A message indicating the success or failure of the delete operation.
     */
    @DeleteMapping("/delete/{id}")
    public String deleteReviewsAndRatingsById(@PathVariable Long id) {
        try {
            log.info("Entered into deleteReviewsAndRatingsById method");
            reviewsAndRatingsService.deleteReviewsAndRatingsById(id);
            return "Reviews and Ratings  deleted successfully";
        }catch (ReviewsAndRatingsNotFoundException e){
            log.error("Invalid ID");
            return "Invalid Id";
        }
    }

    /**
     * Soft delete reviews and ratings by ID.
     *
     * @param id The ID of the reviews and ratings to be soft-deleted.
     * @return A message indicating the success or failure of the soft delete operation.
     */
    @DeleteMapping("/soft-delete/{id}")
    public Boolean softDeleteReviewsAndRatingsById(@PathVariable Long id) {
        try {
            log.info("Entered into softDeleteReviewsAndRatingsById method");
            reviewsAndRatingsService.softDeleteReviewsAndRatingsById(id);
            return true;
        }catch (ReviewsAndRatingsNotFoundException e){
            log.error("Invalid ID");
            return false;
        }
    }

    /**
     * Get all reviews and ratings for a particular restaurant.
     *
     * @param restaurantId The ID of the restaurant to retrieve reviews and ratings for.
     * @return A response entity containing a list of all reviews and ratings for the specified restaurant.
     */
    @GetMapping("/get-all/{restaurantId}")
    public ResponseEntity<Object> getAllReviewsAndRatings(@PathVariable Long restaurantId) {
        try {
            log.info("Entered into getAllReviewsAndRatings method for Restaurant ID: {}", restaurantId);
            List<ReviewsAndRatings> reviewsAndRatings = reviewsAndRatingsService.getAllReviewsAndRatings(restaurantId);
            return ResponseEntity.ok(reviewsAndRatings);
        } catch (Exception e) {
            log.error("Error retrieving all reviews and ratings: " + e.getMessage());
            return new ResponseEntity<>("Error retrieving all reviews and ratings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


/**
 * Service interface for managing reviews and ratings.
 * This interface defines the contract for performing operations related to reviews and ratings.
 *
 * @author agrah.mv
 * @since 30 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.*;
import com.thinkpalm.thinkfood.model.ReviewsAndRatings;

import java.util.List;

/**
 * Service interface for managing reviews and ratings.
 */
public interface ReviewsAndRatingsService {
    /**
     * Creates a new reviews and ratings entry.
     *
     * @param reviewsAndRatings The ReviewsAndRatings object containing the details of the entry.
     * @return The created ReviewsAndRatings object.
     * @throws EmptyInputException      If the input ReviewsAndRatings object is empty or null.
     * @throws RestaurantNotFoundException If the associated restaurant is not found.
     * @throws CustomerNotFoundException   If the associated customer is not found.
     * @throws InvalidRatingException    If the rating value is invalid.
     */
    ReviewsAndRatings createReviewsAndRatings(ReviewsAndRatings reviewsAndRatings) throws EmptyInputException, RestaurantNotFoundException, CustomerNotFoundException, InvalidRatingException;

    /**
     * Retrieves a reviews and ratings entry by its ID.
     *
     * @param id The ID of the reviews and ratings entry to retrieve.
     * @return The ReviewsAndRatings object representing the entry.
     * @throws ReviewsAndRatingsNotFoundException If the reviews and ratings entry with the given ID is not found.
     */
    ReviewsAndRatings getReviewsAndRatingsById(Long id) throws ReviewsAndRatingsNotFoundException;

    /**
     * Updates the details of an existing reviews and ratings entry identified by its ID.
     *
     * @param id The ID of the reviews and ratings entry to update.
     * @param updatedReviewsAndRatings The updated reviews and ratings object with new details.
     * @return The updated ReviewsAndRatings object representing the modified entry.
     * @throws ReviewsAndRatingsNotFoundException If the reviews and ratings entry with the given ID is not found.
     */
    ReviewsAndRatings updateReviewsAndRatingsById(Long id, ReviewsAndRatings updatedReviewsAndRatings) throws  ReviewsAndRatingsNotFoundException;

    /**
     * Deletes a reviews and ratings entry by its ID.
     *
     * @param id The ID of the reviews and ratings entry to delete.
     * @return The ReviewsAndRatings object representing the deleted entry.
     * @throws ReviewsAndRatingsNotFoundException If the reviews and ratings entry with the given ID is not found.
     */
    ReviewsAndRatings deleteReviewsAndRatingsById(Long id) throws ReviewsAndRatingsNotFoundException;

    /**
     * Soft deletes a reviews and ratings entry by marking it as inactive.
     *
     * @param id The ID of the reviews and ratings entry to soft delete.
     * @return A message indicating the success of the soft deletion.
     * @throws ReviewsAndRatingsNotFoundException If the reviews and ratings entry with the given ID is not found.
     */
    String softDeleteReviewsAndRatingsById(Long id) throws ReviewsAndRatingsNotFoundException;


    List<ReviewsAndRatings> getAllReviewsAndRatings(Long restaurantId);
}

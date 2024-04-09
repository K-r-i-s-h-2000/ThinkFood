/**
 * Service implementation for managing reviews and ratings.
 * This class is responsible for handling CRUD operations on reviews and ratings entities.
 *
 * @author agrah.mv
 * @since 30 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.service;


import com.thinkpalm.thinkfood.entity.Customer;
import com.thinkpalm.thinkfood.entity.Restaurant;
import com.thinkpalm.thinkfood.entity.ReviewsAndRatings;
import com.thinkpalm.thinkfood.exception.*;
import com.thinkpalm.thinkfood.repository.CustomerRepository;
import com.thinkpalm.thinkfood.repository.RestaurantRepository;
import com.thinkpalm.thinkfood.repository.ReviewsAndRatingsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the ReviewsAndRatingsService interface for managing reviews and ratings.
 */
@Service
@Log4j2
public class ReviewsAndRatingsServiceImplementation implements ReviewsAndRatingsService {

    @Autowired
    private ReviewsAndRatingsRepository reviewsAndRatingsRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CustomerRepository customerRepository;


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
    @Override
    public com.thinkpalm.thinkfood.model.ReviewsAndRatings createReviewsAndRatings(com.thinkpalm.thinkfood.model.ReviewsAndRatings reviewsAndRatings) throws EmptyInputException, RestaurantNotFoundException, CustomerNotFoundException, InvalidRatingException {
        log.info("Creating a new Reviews and Ratings entry");
        if (reviewsAndRatings.getRating()==null || reviewsAndRatings.getReview()==null || reviewsAndRatings.getCustomerId()==null || reviewsAndRatings.getRestaurantId()==null){
            throw new EmptyInputException("All Fields are Mandatory");
        }

        Integer rating = reviewsAndRatings.getRating();
        if (rating < 1 || rating > 5) {
            throw new InvalidRatingException("Rating should be between 1 and 5");
        }

        ReviewsAndRatings entity = new ReviewsAndRatings();

        Long customerId =reviewsAndRatings.getCustomerId();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found with ID: " + customerId));


        Long restaurantId = reviewsAndRatings.getRestaurantId();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId));

        if(!customer.getIsActive()){
            throw new CustomerNotFoundException("Customer with id " + customerId + " is not active");
        }

        if(!restaurant.getIsActive()){
            throw new RestaurantNotFoundException("Restaurant with id " + restaurantId + " is not active");
        }

        entity.setCustomer(customer);
        entity.setRestaurant(restaurant);
        entity.setReview(reviewsAndRatings.getReview());
        entity.setRating(reviewsAndRatings.getRating());
        entity.setIsActive(true);

        entity =reviewsAndRatingsRepository.save(entity);

        com.thinkpalm.thinkfood.model.ReviewsAndRatings model = new com.thinkpalm.thinkfood.model.ReviewsAndRatings();
        BeanUtils.copyProperties(entity, model);
        model.setCustomerId(customerId);
        model.setRestaurantId(restaurantId);

        log.info("Reviews and Ratings entry created successfully");
        return model;
    }

    /**
     * Retrieves a reviews and ratings entry by its ID.
     *
     * @param id The ID of the reviews and ratings entry to retrieve.
     * @return The ReviewsAndRatings object representing the entry.
     * @throws ReviewsAndRatingsNotFoundException If the reviews and ratings entry with the given ID is not found.
     */
    @Override
    public com.thinkpalm.thinkfood.model.ReviewsAndRatings getReviewsAndRatingsById(Long id) throws ReviewsAndRatingsNotFoundException {
        log.info("Retrieving Reviews and Ratings entry with ID: " + id);

        Optional<ReviewsAndRatings> entityWrapper = reviewsAndRatingsRepository.findById(id);
        if (entityWrapper.isEmpty()) {
            throw new ReviewsAndRatingsNotFoundException("Reviews and Ratings is not found with id: " + id);
        }

        ReviewsAndRatings entity = entityWrapper.get();

        if (!entity.getIsActive()) {
            throw new ReviewsAndRatingsNotFoundException("Reviews and Ratings is not active with id: " + id);
        }

        com.thinkpalm.thinkfood.model.ReviewsAndRatings model = new com.thinkpalm.thinkfood.model.ReviewsAndRatings();

        BeanUtils.copyProperties(entity, model);

        // Copy customerId, restaurantId and customerName from the entity to the model
        model.setCustomerId(entity.getCustomer().getId());
        model.setRestaurantId(entity.getRestaurant().getId());
        model.setCustomerName(entity.getCustomer().getCustomerName());

        log.info("Reviews and Ratings entry with ID " + id + " retrieved successfully");
        return model;
    }

    /**
     * Updates the details of an existing reviews and ratings entry identified by its ID.
     *
     * @param id The ID of the reviews and ratings entry to update.
     * @param updatedReviewsAndRatings The updated reviews and ratings object with new details.
     * @return The updated ReviewsAndRatings object representing the modified entry.
     * @throws ReviewsAndRatingsNotFoundException If the reviews and ratings entry with the given ID is not found.
     */
    @Override
    public com.thinkpalm.thinkfood.model.ReviewsAndRatings updateReviewsAndRatingsById(Long id, com.thinkpalm.thinkfood.model.ReviewsAndRatings updatedReviewsAndRatings) throws  ReviewsAndRatingsNotFoundException {
        log.info("Updating Reviews and Ratings entry with ID: " + id);

        ReviewsAndRatings entity = reviewsAndRatingsRepository.findById(id)
                .orElseThrow(()-> new ReviewsAndRatingsNotFoundException("Reviews and Ratings is not found with id: "+id));
        if(entity.getIsActive()) {
            if (updatedReviewsAndRatings.getRating() != null) {
                entity.setRating(updatedReviewsAndRatings.getRating());
            }

            if (updatedReviewsAndRatings.getReview() != null) {
                entity.setReview(updatedReviewsAndRatings.getReview());
            }


            entity = reviewsAndRatingsRepository.save(entity);

            com.thinkpalm.thinkfood.model.ReviewsAndRatings model = new com.thinkpalm.thinkfood.model.ReviewsAndRatings();

            BeanUtils.copyProperties(entity, model);
            model.setCustomerId(entity.getCustomer().getId());
            model.setRestaurantId(entity.getRestaurant().getId());

            log.info("Reviews and Ratings entry with ID " + id + " updated successfully");
            return model;
        }else{
            throw new ReviewsAndRatingsNotFoundException("Cannot update an inactive Reviews and Ratings");
        }
    }

    /**
     * Deletes a reviews and ratings entry by its ID.
     *
     * @param id The ID of the reviews and ratings entry to delete.
     * @return The ReviewsAndRatings object representing the deleted entry.
     * @throws ReviewsAndRatingsNotFoundException If the reviews and ratings entry with the given ID is not found.
     */
    @Override
    public com.thinkpalm.thinkfood.model.ReviewsAndRatings deleteReviewsAndRatingsById(Long id) throws ReviewsAndRatingsNotFoundException {
        log.info("Deleting Reviews and Ratings entry with ID: " + id);

        Optional<ReviewsAndRatings> entityWrapper = reviewsAndRatingsRepository.findById(id);

        if (entityWrapper.isEmpty()) {
            throw new ReviewsAndRatingsNotFoundException("Reviews and Ratings not found with ID: " + id);
        }

        ReviewsAndRatings entity = entityWrapper.get();

        com.thinkpalm.thinkfood.model.ReviewsAndRatings model = new com.thinkpalm.thinkfood.model.ReviewsAndRatings();

        BeanUtils.copyProperties(entity, model);
        model.setCustomerId(entity.getCustomer().getId());
        model.setRestaurantId(entity.getRestaurant().getId());

        reviewsAndRatingsRepository.deleteById(id);

        log.info("Reviews and Ratings entry with ID " + id + " deleted successfully");
        return model;
    }

    /**
     * Soft deletes a reviews and ratings entry by marking it as inactive.
     *
     * @param id The ID of the reviews and ratings entry to soft delete.
     * @return A message indicating the success of the soft deletion.
     * @throws ReviewsAndRatingsNotFoundException If the reviews and ratings entry with the given ID is not found.
     */
    @Override
    public String softDeleteReviewsAndRatingsById(Long id) throws ReviewsAndRatingsNotFoundException {
        log.info("Soft deleting Reviews and Ratings entry with ID: " + id);

        Optional<ReviewsAndRatings> entityWrapper = reviewsAndRatingsRepository.findById(id);

        if (entityWrapper.isEmpty()) {
            throw new ReviewsAndRatingsNotFoundException("Reviews and Ratings not found with ID: " + id);
        }

        ReviewsAndRatings entity = entityWrapper.get();
        entity.setIsActive(false);
        reviewsAndRatingsRepository.save(entity);

        log.info("Reviews and Ratings entry with ID " + id + " has been soft deleted");
        return "Reviews and Ratings With Id "+id+" deleted successfully!";
    }


//    @Override
//    public List<com.thinkpalm.thinkfood.model.ReviewsAndRatings> getAllReviewsAndRatings(Long restaurantId) {
//        log.info("Retrieving all Reviews and Ratings entries for Restaurant ID: {}", restaurantId);
//
//        List<ReviewsAndRatings> entityList = reviewsAndRatingsRepository.findAll();
//
//        List<com.thinkpalm.thinkfood.model.ReviewsAndRatings> modelList = entityList.stream()
//                .filter(entity -> entity.getIsActive() && entity.getRestaurant().getId().equals(restaurantId))
//                .map(entity -> {
//                    com.thinkpalm.thinkfood.model.ReviewsAndRatings model = new com.thinkpalm.thinkfood.model.ReviewsAndRatings();
//                    BeanUtils.copyProperties(entity, model);
//                    model.setCustomerId(entity.getCustomer().getId());
//                    model.setRestaurantId(entity.getRestaurant().getId());
//                    model.setCustomerName(entity.getCustomer().getCustomerName());
//                    return model;
//                })
//                .collect(Collectors.toList());
//
//        log.info("All Reviews and Ratings entries for Restaurant ID {} retrieved successfully", restaurantId);
//        return modelList;
//    }

    @Override
    public List<com.thinkpalm.thinkfood.model.ReviewsAndRatings> getAllReviewsAndRatings(Long restaurantId) {
        log.info("Retrieving all Reviews and Ratings entries for Restaurant ID: {}", restaurantId);

        List<ReviewsAndRatings> entityList = reviewsAndRatingsRepository.findAll();

        List<com.thinkpalm.thinkfood.model.ReviewsAndRatings> modelList = entityList.stream()
                .filter(entity -> entity.getIsActive() && entity.getRestaurant().getId().equals(restaurantId))
                .sorted(Comparator.comparing(ReviewsAndRatings::getLastModifiedDateTime).reversed()) // Sort by review date in descending order
                .map(entity -> {
                    com.thinkpalm.thinkfood.model.ReviewsAndRatings model = new com.thinkpalm.thinkfood.model.ReviewsAndRatings();
                    BeanUtils.copyProperties(entity, model);
                    model.setCustomerId(entity.getCustomer().getId());
                    model.setRestaurantId(entity.getRestaurant().getId());
                    model.setCustomerName(entity.getCustomer().getCustomerName());
                    return model;
                })
                .collect(Collectors.toList());

        log.info("All Reviews and Ratings entries for Restaurant ID {} retrieved successfully", restaurantId);
        return modelList;
    }

}


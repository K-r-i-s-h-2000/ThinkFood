package com.thinkpalm.thinkfood.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.thinkpalm.thinkfood.entity.Customer;
import com.thinkpalm.thinkfood.entity.Restaurant;
import com.thinkpalm.thinkfood.entity.ReviewsAndRatings;
import com.thinkpalm.thinkfood.exception.*;
import com.thinkpalm.thinkfood.repository.CustomerRepository;
import com.thinkpalm.thinkfood.repository.RestaurantRepository;
import com.thinkpalm.thinkfood.repository.ReviewsAndRatingsRepository;
import com.thinkpalm.thinkfood.service.ReviewsAndRatingsServiceImplementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class ReviewsAndRatingsServiceImplementationTest {

    @InjectMocks
    private ReviewsAndRatingsServiceImplementation reviewsAndRatingsService;

    @Mock
    private ReviewsAndRatingsRepository reviewsAndRatingsRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void testCreateReviewsAndRatingsSuccess() throws EmptyInputException, RestaurantNotFoundException, CustomerNotFoundException, InvalidRatingException {

        com.thinkpalm.thinkfood.model.ReviewsAndRatings inputModel = new com.thinkpalm.thinkfood.model.ReviewsAndRatings();
        inputModel.setRating(4);
        inputModel.setReview("Great food!");
        inputModel.setCustomerId(1L);
        inputModel.setRestaurantId(2L);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setIsActive(true);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Restaurant restaurant = new Restaurant();
        restaurant.setId(2L);
        restaurant.setIsActive(true);
        when(restaurantRepository.findById(2L)).thenReturn(Optional.of(restaurant));

        ReviewsAndRatings savedEntity = new ReviewsAndRatings();
        savedEntity.setId(1L);
        savedEntity.setRating(inputModel.getRating());
        savedEntity.setReview(inputModel.getReview());
        savedEntity.setRestaurant(restaurant);
        savedEntity.setCustomer(customer);
        savedEntity.setIsActive(true);
        when(reviewsAndRatingsRepository.save(any(ReviewsAndRatings.class))).thenReturn(savedEntity);

        com.thinkpalm.thinkfood.model.ReviewsAndRatings result = reviewsAndRatingsService.createReviewsAndRatings(inputModel);

        assertEquals(1L, result.getId());
        assertEquals(inputModel.getRating(), result.getRating());
        assertEquals(inputModel.getReview(), result.getReview());
        assertEquals(1L, result.getCustomerId());
        assertEquals(2L, result.getRestaurantId());

    }

    @Test
    public void testCreateReviewsAndRatingsFailureInvalidRating()  {

        com.thinkpalm.thinkfood.model.ReviewsAndRatings inputModel = new com.thinkpalm.thinkfood.model.ReviewsAndRatings();
        inputModel.setRating(6); // Invalid rating
        inputModel.setReview("Great food!");
        inputModel.setCustomerId(1L);
        inputModel.setRestaurantId(2L);


        assertThrows(InvalidRatingException.class, () -> reviewsAndRatingsService.createReviewsAndRatings(inputModel));
    }

    @Test
    public void testGetReviewsAndRatingsByIdSuccess() throws ReviewsAndRatingsNotFoundException {

        Long reviewsAndRatingsId = 1L;

        ReviewsAndRatings entity = new ReviewsAndRatings();
        entity.setId(reviewsAndRatingsId);
        entity.setIsActive(true);

        Customer customer = new Customer();
        customer.setId(2L);
        customer.setCustomerName("John Doe");
        customer.setIsActive(true);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(3L);
        restaurant.setIsActive(true);

        entity.setCustomer(customer);
        entity.setRestaurant(restaurant);

        when(reviewsAndRatingsRepository.findById(reviewsAndRatingsId)).thenReturn(Optional.of(entity));


        com.thinkpalm.thinkfood.model.ReviewsAndRatings result = reviewsAndRatingsService.getReviewsAndRatingsById(reviewsAndRatingsId);


        assertNotNull(result);
        assertEquals(reviewsAndRatingsId, result.getId());
        assertEquals(2L, result.getCustomerId());
        assertEquals(3L, result.getRestaurantId());
        assertEquals("John Doe", result.getCustomerName());
    }

    @Test
    public void testGetReviewsAndRatingsByIdFailureNotFound() {

        Long reviewsAndRatingsId = 1L;

        when(reviewsAndRatingsRepository.findById(reviewsAndRatingsId)).thenReturn(Optional.empty());

        assertThrows(ReviewsAndRatingsNotFoundException.class, () -> reviewsAndRatingsService.getReviewsAndRatingsById(reviewsAndRatingsId));
    }

    @Test
    public void testUpdateReviewsAndRatingsByIdSuccess() throws ReviewsAndRatingsNotFoundException {

        Long reviewsAndRatingsId = 1L;

        com.thinkpalm.thinkfood.model.ReviewsAndRatings updatedModel = new com.thinkpalm.thinkfood.model.ReviewsAndRatings();
        updatedModel.setRating(4);
        updatedModel.setReview("Updated review");

        ReviewsAndRatings entity = new ReviewsAndRatings();
        entity.setId(reviewsAndRatingsId);
        entity.setRating(3);
        entity.setReview("Old review");
        entity.setIsActive(true);

        Customer customer = new Customer();
        customer.setId(2L);
        customer.setIsActive(true);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(3L);
        restaurant.setIsActive(true);

        entity.setCustomer(customer);
        entity.setRestaurant(restaurant);

        when(reviewsAndRatingsRepository.findById(reviewsAndRatingsId)).thenReturn(Optional.of(entity));
        when(reviewsAndRatingsRepository.save(entity)).thenReturn(entity);

        com.thinkpalm.thinkfood.model.ReviewsAndRatings result = reviewsAndRatingsService.updateReviewsAndRatingsById(reviewsAndRatingsId, updatedModel);

        assertNotNull(result);
        assertEquals(reviewsAndRatingsId, result.getId());
        assertEquals(4, result.getRating());
        assertEquals("Updated review", result.getReview());
        assertEquals(2L, result.getCustomerId());
        assertEquals(3L, result.getRestaurantId());
    }

    @Test
    public void testUpdateReviewsAndRatingsByIdFailureNotFound() {

        Long reviewsAndRatingsId = 1L;

        com.thinkpalm.thinkfood.model.ReviewsAndRatings updatedModel = new com.thinkpalm.thinkfood.model.ReviewsAndRatings();
        updatedModel.setRating(4);
        updatedModel.setReview("Updated review");

        when(reviewsAndRatingsRepository.findById(reviewsAndRatingsId)).thenReturn(Optional.empty());

        assertThrows(ReviewsAndRatingsNotFoundException.class, () -> reviewsAndRatingsService.updateReviewsAndRatingsById(reviewsAndRatingsId, updatedModel));
    }

    @Test
    public void testDeleteReviewsAndRatingsByIdSuccess() throws ReviewsAndRatingsNotFoundException {

        Long reviewsAndRatingsId = 1L;

        ReviewsAndRatings entity = new ReviewsAndRatings();
        entity.setId(reviewsAndRatingsId);
        entity.setIsActive(true);

        Customer customer = new Customer();
        customer.setId(2L);
        customer.setIsActive(true);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(3L);
        restaurant.setIsActive(true);

        entity.setCustomer(customer);
        entity.setRestaurant(restaurant);

        when(reviewsAndRatingsRepository.findById(reviewsAndRatingsId)).thenReturn(Optional.of(entity));


        com.thinkpalm.thinkfood.model.ReviewsAndRatings result = reviewsAndRatingsService.deleteReviewsAndRatingsById(reviewsAndRatingsId);

        // Assert
        assertNotNull(result);
        assertEquals(reviewsAndRatingsId, result.getId());
        assertEquals(2L, result.getCustomerId());
        assertEquals(3L, result.getRestaurantId());

    }

    @Test
    public void testDeleteReviewsAndRatingsByIdFailureNotFound() {

        Long reviewsAndRatingsId = 1L;

        when(reviewsAndRatingsRepository.findById(reviewsAndRatingsId)).thenReturn(Optional.empty());

        assertThrows(ReviewsAndRatingsNotFoundException.class, () -> reviewsAndRatingsService.deleteReviewsAndRatingsById(reviewsAndRatingsId));
    }

    @Test
    public void testSoftDeleteReviewsAndRatingsByIdSuccess() throws ReviewsAndRatingsNotFoundException {

        Long reviewsAndRatingsId = 1L;

        ReviewsAndRatings entity = new ReviewsAndRatings();
        entity.setId(reviewsAndRatingsId);
        entity.setIsActive(true);

        when(reviewsAndRatingsRepository.findById(reviewsAndRatingsId)).thenReturn(Optional.of(entity));
        when(reviewsAndRatingsRepository.save(entity)).thenReturn(entity);

        String result = reviewsAndRatingsService.softDeleteReviewsAndRatingsById(reviewsAndRatingsId);

        assertNotNull(result);
        assertEquals("Reviews and Ratings With Id 1 deleted successfully!", result);
        assertFalse(entity.getIsActive());
    }

    @Test
    public void testSoftDeleteReviewsAndRatingsByIdFailureNotFound() {

        Long reviewsAndRatingsId = 1L;

        when(reviewsAndRatingsRepository.findById(reviewsAndRatingsId)).thenReturn(Optional.empty());

        assertThrows(ReviewsAndRatingsNotFoundException.class, () -> reviewsAndRatingsService.softDeleteReviewsAndRatingsById(reviewsAndRatingsId));
    }



}

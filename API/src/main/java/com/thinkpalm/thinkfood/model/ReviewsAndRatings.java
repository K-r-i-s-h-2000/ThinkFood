/**
 * Model class representing reviews and ratings.
 * This class is used to hold information about reviews and ratings, including their ID, customer ID and name,
 * restaurant ID, review text, and rating.
 *
 *
 * @author agrah.mv
 * @since 30 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsAndRatings {


    /**
     * The unique identifier for the reviews and ratings entry.
     */
    private Long id;

    /**
     * The unique identifier for the customer submitting the review.
     */
    private Long customerId;

    /**
     * The name of the customer submitting the review.
     */
    private String customerName;

    /**
     * The unique identifier for the restaurant being reviewed.
     */
    private Long restaurantId;

    /**
     * The text of the review.
     */
    private String review;

    /**
     * The numerical rating given in the review.
     */
    private Integer rating;

    private LocalDateTime lastModifiedDateTime;


}


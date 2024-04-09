/**
 * Entity class representing reviews and ratings for CRUD operations.
 * This class is designed to facilitate modifications to the corresponding "reviews_and_ratings" table.
 *
 * <p>{@code ReviewsAndRatings} includes details such as the associated customer, restaurant, review text,
 * rating, and a flag indicating whether the reviews and ratings entry is currently active or not.</p>
 *
 * @author agrah.mv
 * @since 30 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "reviews_and_ratings")
public class ReviewsAndRatings extends EntityDoc{


    /**
     * The customer who submitted the review and rating.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * The restaurant for which the review and rating are submitted.
     */
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    /**
     * The text of the review.
     */
    @Column(name = "review", columnDefinition = "TEXT")
    private String review;

    /**
     * The numerical rating given in the review.
     */
    @Column(name = "rating")
    private Integer rating;

    /**
     * Indicates whether the reviews and ratings entry is currently active.
     */
    @Column(name="is_active")
    private Boolean isActive=false;
}

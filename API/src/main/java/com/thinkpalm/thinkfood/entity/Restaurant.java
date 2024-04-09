/**
 * Represents a restaurant entity in the application model.
 * This class is used to store information about a restaurant.
 * author: Sharon Sam
 * since : 26 October 2023
 * version : 2.0
 */

package com.thinkpalm.thinkfood.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="restaurant")
public class  Restaurant extends EntityDoc{

    /**
     * The name of the restaurant.
     */

    @Column(name = "restaurant_name")
    private String restaurantName;

    /**
     * A brief description of the restaurant.
     */

    @Column(name = "restaurant_description")
    private String restaurantDescription;

    /**
     * The latitude coordinate of the restaurant's location.
     */

    @Column(name = "restaurant_latitude")
    private Double restaurantLatitude;

    /**
     * The longitude coordinate of the restaurant's location.
     */

    @Column(name = "restaurant_longitude")
    private Double restaurantLongitude;

    /**
     * The contact phone number for the restaurant.
     */

    @Column(name = "restaurant_contact")
    private String phoneNumber;

    /**
     * The email address associated with the restaurant.
     */

    @Column(name = "restaurant_email")
    private String email;

    /**
     * Indicates whether the restaurant is currently available.
     */

    @Column(name = "restaurant_availability")
    private boolean restaurantAvailability;

    /**
     * The user associated with the restaurant.
     */

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Indicates whether the restaurant item is deleted or not.
     */

    @Builder.Default
    @Column(name="is_active")
    private Boolean isActive=false;
    @Column(name = "image")
    private String image;

}

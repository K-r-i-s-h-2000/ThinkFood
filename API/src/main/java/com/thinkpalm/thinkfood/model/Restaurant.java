/**
 * Represents a restaurant in the application model.
 * This class is used to store information about restaurant.
 * author: Sharon Sam
 * since : 26 October 2023
 * version : 2.0
 */


package com.thinkpalm.thinkfood.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Restaurant {

    /**
     * The unique identifier for the restaurant.
     */

    private Long id;

    /**
     * The name of the restaurant.
     */

    private String restaurantName;

    /**
     * A description of the restaurant.
     */

    private String restaurantDescription;

    /**
     * The latitude coordinate of the restaurant's location.
     */

    private Double restaurantLatitude;

    /**
     * The longitude coordinate of the restaurant's location.
     */

    private Double restaurantLongitude;

    /**
     * The phone number of the restaurant.
     */

    private String phoneNumber;

    /**
     * The email address of the restaurant.
     */

    private String email;


    /**
     * Indicates whether the restaurant is currently available.
     */

    private Boolean restaurantAvailability;
    private String image;

}

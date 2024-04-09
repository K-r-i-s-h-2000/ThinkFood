/**
 * Represents a menu item in the application model.
 * This class is used to store information about a menu item.
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
public class Menu {

    /**
     * The unique identifier for the menu item.
     */

    private Long id;

    /**
     * The identifier of the restaurant to which this menu item belongs.
     */

    private Long restaurantId;

    /**
     * The identifier of the item associated with this menu entry.
     */

    private Long itemId;

    /**
     * A brief description of the menu item.
     */

    private String itemDescription;

    /**
     * The price of the menu item.
     */

    private Double itemPrice;

    /**
     * The preparation time in minutes for this menu item.
     */

    private Integer preparationTime;

    /**
     * Indicates whether the menu item is currently available.
     */

    private Boolean itemAvailability;


}

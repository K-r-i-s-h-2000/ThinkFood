/**
 * Model class to display essential information.
 * This class is to get data and display..
 * @author: Rinkle Rose Renny
 * @since : 31 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Search {

    /**
     * Id of the item
     */
    private Long itemId;

    /**
     * Name of the item
     */
    private String itemName;
    /**
     * Price of the item.
     */
    private Double itemPrice;

    /**
     * Id of the restaurant
     */

    private Long restaurantId;
    /**
     * Name of the restaurant in which the item is present.
     */
    private String restaurantName;
    private String image;

    private Long menuId;
    private String restaurantImage;
    private String itemDescription;
    private boolean itemAvailability;
    private Integer preparationTime;
    private String restaurantDescription;
    private String restaurantEmail;
    private String restaurantPhoneNumber;

}

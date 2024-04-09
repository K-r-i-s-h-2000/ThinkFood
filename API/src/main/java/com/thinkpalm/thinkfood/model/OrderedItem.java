package com.thinkpalm.thinkfood.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents Ordered Items
 * @version 2.0
 * @since 31/10/23
 * @author daan.j
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderedItem {

    /**
     * The name of the ordered item.
     */
    private String name;

    /**
     * The quantity of the ordered item.
     */
    private Integer quantity;

    /**
     * The subtotal cost for the ordered item.
     */
    private Double subTotal;

    /**
     * The price of a single unit of the ordered item.
     */
    private Double price;

    /**
     * The restaurant associated with the ordered item.
     */
    private String restaurant;

    private String restaurantImage;

    private String itemImage;
}
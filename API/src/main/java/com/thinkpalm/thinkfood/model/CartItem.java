package com.thinkpalm.thinkfood.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Model class representing an item in a shopping cart.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CartItem {
    /**
     * The unique identifier of the cart item.
     */
    private Long Id;
    /**
     * The unique identifier of the menu item associated with this cart item.
     */
    private Long itemId;
    /**
     * The name of this menu item in the cart.
     */
    private String itemName;
    /**
     * The quantity of this menu item in the cart.
     */
    private int quantity;
    /**
     * The price of a single unit of this menu item.
     */
    private Double itemPrice;
    /**
     * The subtotal for this cart item, calculated as the product of quantity and item price.
     */
    private Double subtotal;

    private String itemImage;


}

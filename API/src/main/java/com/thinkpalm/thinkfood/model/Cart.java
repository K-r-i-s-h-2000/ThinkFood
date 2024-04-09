package com.thinkpalm.thinkfood.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
/**
 * Model class representing a shopping cart in the system.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cart {
    /**
     * The unique identifier of the cart.
     */
    private Long cart;
    /**
     * The unique identifier of the customer associated with this cart.
     */
    private Long customer;
    /**
     * A list of cart items added to the cart.
     */
    private List<CartItem> cartItem;
    /**
     * The total amount of the cart, which is the sum of the subtotals of all cart items.
     */
    private Double totalAmount;

}

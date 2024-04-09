package com.thinkpalm.thinkfood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Entity class representing a cart item in the system.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cart_item")
public class CartItem extends EntityDoc {
    /**
     * The cart to which this cart item belongs.
     */
    @ManyToOne
    @JoinColumn(name="cart_id", referencedColumnName = "id")
    private Cart cart;
    /**
     * The menu item associated with this cart item.
     */
    @OneToOne
    @JoinColumn(name="menu_id", referencedColumnName = "id")
    private Menu menu;
    /**
     * The quantity of this menu item in the cart.
     */
    @Column(name="quantity",nullable = false)
    private int quantity;
    /**
     * The price of a single unit of this menu item.
     */
    @Column(name="item_price",nullable = false)
    private double itemPrice;
    /**
     * The subtotal of this cart item (quantity * item price).
     */
    @Column(name="subtotal")
    private double subtotal;

    @Column(name="is_active")
    private Boolean isActive=false;

}

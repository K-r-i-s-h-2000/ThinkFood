package com.thinkpalm.thinkfood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
/**
 * This class represents the entity for a shopping cart.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cart")
public class Cart extends EntityDoc{
    /**
     * The customer associated with this cart.
     */
    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private Customer customer;
    /**
     * The list of items in the shopping cart (cart items).
     */
    @OneToMany(mappedBy = "cart", cascade=CascadeType.ALL)
    private List<CartItem> cartItems;
    /**
     * The total amount for all items in the cart.
     */
    @Column(name="total_amount", nullable=false)
    private Double totalAmount;

    @Column(name="is_active")
    private Boolean isActive=false;

}

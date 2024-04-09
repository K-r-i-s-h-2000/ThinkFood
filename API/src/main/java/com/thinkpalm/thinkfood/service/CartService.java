package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.NotAvailableException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.exception.NotSameRestaurantException;
import com.thinkpalm.thinkfood.model.Cart;

import java.util.List;
/**
 * Service interface for managing cart-related operations.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */

public interface CartService {
    Cart createAndUpdateCart(Cart cartRequest) throws NotFoundException, NotSameRestaurantException, NotAvailableException;

    /**
     * Create a new cart with the provided details.
     *
     * @param cart The cart details to create.
     * @return The created cart.
     * @throws NotFoundException if any required entities (e.g., customer or menu) are not found.
     */
    Cart createCart(com.thinkpalm.thinkfood.model.Cart cart) throws NotFoundException;

    /**
     * Find a cart by its unique identifier.
     *
     * @param cartId The unique identifier of the cart to find.
     * @return The found cart.
     * @throws NotFoundException if the cart is not found.
     */
    Cart findCartById(Long cartId) throws NotFoundException;

    Cart findCartByCustomerId(Long customerId) throws NotFoundException;

    /**
     * Add an item to the specified cart.
     *
     * @param cartRequest The cart with the new item to add.
     * @return The cart after adding the item.
     * @throws NotFoundException if the item is not available or not from the same restaurant.
     */
    Cart updateCart(Cart cartRequest) throws NotFoundException, NotSameRestaurantException, NotAvailableException;


    Cart updateQuantity(Cart cartRequest) throws NotFoundException, NotSameRestaurantException, NotAvailableException;

    /**
     * Delete an item from the specified cart.
     *
     * @param cartId The unique identifier of the cart.
     * @param id     The unique identifier of the item to delete.
     * @return A message indicating the success of the operation.
     */
    String deleteItemCart(Long cartId,Long id);
    /**
     * Delete a cart by its unique identifier.
     *
     * @param id The unique identifier of the cart to delete.
     * @return A message indicating the success of the operation.
     */
    Cart deleteCart(Long id);

    String softDeleteCart(Long id) throws NotFoundException;

    String softDeleteCartItem(Long cartId, Long id) throws NotFoundException;
}

package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Repository interface for managing CartItem entities in the database.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    /**
     * Retrieves a list of cart items associated with a given cart identifier.
     *
     * @param cartId The unique identifier of the cart.
     * @return A list of cart items associated with the specified cart.
     */
    List<CartItem> findAllByCartId(Long cartId);
}

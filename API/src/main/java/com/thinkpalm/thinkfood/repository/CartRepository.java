package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.Cart;
import com.thinkpalm.thinkfood.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repository interface for managing Cart entities in the database.
 * @author krishna.c
 * @version 31/10/2023
 * @since 31/10/2023
 */
@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
     /**
      * Retrieves a list of cart items associated with a cart by its unique identifier.
      *
      * @param id The unique identifier of the cart.
      * @return A list of cart items associated with the specified cart.
      */
     List<CartItem> findAllById(long id);

     Cart findCartByCustomerId(Long customerId);

     List<Cart> findByCustomer_IdAndIsActiveTrue(Long customerId);
}

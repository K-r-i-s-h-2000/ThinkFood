package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 * Repository interface for Order entities.
 * This interface provides methods for performing CRUD operations on Order entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {


    /**
     * Retrieve an order by its unique identifier.
     *
     * @param id The unique identifier of the order.
     * @return An Optional containing the order, or empty if not found.
     */

    Optional<Order> findById(Long id);

    /**
     * Retrieve a list of orders associated with a specific customer.
     *
     * @param customerId The unique identifier of the customer.
     * @return A list of orders associated with the customer.
     */

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByRestaurantId(Long restaurantId);


   // Page<Order> findByRestaurantId(Long restaurantId, Pageable pageable);




}

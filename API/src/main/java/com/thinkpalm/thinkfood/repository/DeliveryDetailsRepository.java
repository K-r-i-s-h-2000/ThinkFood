/**
 * Repository interface for order delivery's CRUD operations.
 * This interface contains queries for crud operations to manipulate databases.
 * @author: Rinkle Rose Renny
 * @since : 3 November 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.DeliveryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;


public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails, Long> {

    @Query(value = "SELECT *  FROM delivery_details where delivery_details.delivery_id = ?1",nativeQuery = true)
    List<DeliveryDetails> findAllByDelivery_Id(Long deliveryId);

    @Query(value = "SELECT *  FROM delivery_details where delivery_details.order_id = ?1",nativeQuery = true)
    Optional<DeliveryDetails> findAllByOrder_Id(Long orderId);
}

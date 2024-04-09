/**
 * Repository interface for delivery agents CRUD operations.
 * This interface contains queries for crud operations to manipulate databases.
 * @author: Rinkle Rose Renny
 * @since : 26 October 2023
 * @version : 2.0
 */

package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findByUserId(Long id);



}

/**
 * Repository interface for performing CRUD operations on the Restaurant entity.
 * This interface provides methods to interact with the underlying database table.
 *
 * Author: Sharon Sam
 * Since: 26 October 2023
 * Version: 2.0
 */

package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    /**
     * Retrieve a restaurant by its unique ID.
     *
     * @param id The ID of the restaurant to retrieve.
     * @return An Optional containing the restaurant with the specified ID if found, or an empty Optional if not found.
     */
    Optional<Restaurant> findById(Long id);

    /**
     * Retrieve a paginated list of all restaurants.
     *
     * @param pageable The pagination information (page number, page size).
     * @return A Page containing a list of restaurants based on the provided pagination.
     */
    Page<Restaurant> findAll(Pageable pageable);

}

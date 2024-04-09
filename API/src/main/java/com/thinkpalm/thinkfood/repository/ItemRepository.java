/**
 * Repository interface for performing CRUD operations on the Item entity.
 * This interface provides methods to interact with the underlying database table.
 *
 *
 * @author agrah.mv
 * @since 27 October 2023
 * @version 2.0
 *
 */
package com.thinkpalm.thinkfood.repository;


import com.thinkpalm.thinkfood.entity.Category;
import com.thinkpalm.thinkfood.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Retrieve a list of items associated with a specific category.
     *
     * @param categoryEntity The category for which to retrieve items.
     * @return A list of items associated with the specified category.
     */
    List<Item> findByCategory(Category categoryEntity);

    /**
     * Retrieve a paginated list of items by their category.
     *
     * @param category The category for which to retrieve items.
     * @param pageable The pagination information (page number, page size).
     * @return A Page containing a list of items associated with the specified category.
     */
    Page<Item> findByCategory(Category category, Pageable pageable);


//    Optional<Item> findByItemNameAndIsActiveTrue(String itemName);
//
//    Optional<Item> findByItemNameIgnoreCaseAndIsActiveTrue(String itemName);

    List<Item> findByIsActive(boolean isActive);

}
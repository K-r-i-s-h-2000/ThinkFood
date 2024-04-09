/**
 * Repository interface for performing CRUD operations on the Menu entity.
 * This interface provides methods to interact with the underlying database table.
 *
 * Author: Sharon Sam
 * Since: 26 October 2023
 * Version: 2.0
 */

package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {

    /**
     * Retrieve a menu by its unique ID.
     *
     * @param id The ID of the menu to retrieve.
     * @return An Optional containing the menu with the specified ID if found, or an empty Optional if not found.
     */
    Optional<Menu> findById(Long id);

    /**
     * Retrieve a list of menus associated with a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant for which to retrieve menus.
     * @return A list of menus associated with the specified restaurant.
     */
    List<Menu> findByRestaurantId(Long restaurantId);

    /**
     * Retrieve a paginated list of menus by their item ID.
     *
     * @param itemId The ID of the item for which to retrieve menus.
     * @return A Page containing a list of menus associated with the specified item.
     */

    List<Menu> findAllByitemId(Long itemId);

    /**
     * Retrieve a paginated list of menus by their restaurant ID.
     *
     * @param restaurantId The ID of the restaurant for which to retrieve menus.
     * @param pageRequest The pagination information (page number, page size).
     * @return A Page containing a list of menus associated with the specified restaurant.
     */

    Page<Menu> findAllByrestaurantId(Long restaurantId, PageRequest pageRequest);

}

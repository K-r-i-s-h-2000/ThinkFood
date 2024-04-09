/**
 * Service interface for managing menus.
 * This interface defines the contract for performing CRUD operations on menus.
 *
 * @author Sharon Sam
 * @since 26 October 2023
 * @version 2.0
 */

package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.*;
import com.thinkpalm.thinkfood.model.Menu;

import com.thinkpalm.thinkfood.model.Restaurant;
import com.thinkpalm.thinkfood.model.UpdateMenu;

public interface MenuService {
    /**
     * Retrieve a menu by its ID.
     *
     * @param id The ID of the menu to retrieve.
     * @return The menu with the specified ID.
     * @throws NotFoundException If the menu with the given ID is not found.
     */

    Menu getMenuById(Long id) throws NotFoundException;


    /**
     * Create a new menu.
     *
     * @param menu The menu to create.
     * @return The created menu.
     * @throws EmptyInputException If any required field is empty in the input.
     * @throws NotFoundException If the associated restaurant is not found.
     */
    Menu createMenu(Menu menu) throws EmptyInputException, NotFoundException;

    /**
     * Update an existing menu.
     *
     * @param id The ID of the menu to update.
     * @param updatedMenu The updated menu data.
     * @return The updated menu.
     * @throws NotFoundException If the menu with the given ID is not found.
     */
    UpdateMenu updateMenu(long id, UpdateMenu updatedMenu) throws NotFoundException;

    /**
     * Delete a menu by its ID.
     *
     * @param id The ID of the menu to delete.
     * @return The deleted menu.
     * @throws NotFoundException If the menu with the given ID is not found.
     */
    Menu deleteMenuById(Long id) throws NotFoundException;

    /**
     * Soft delete a menu by setting the 'is_active' flag to false.
     *
     * @param id The ID of the menu to be soft-deleted.
     * @return A message indicating the success of the soft delete operation.
     * @throws NotFoundException If the menu with the given ID is not found.
     */

    String softDeleteMenuById(Long id) throws NotFoundException;
}

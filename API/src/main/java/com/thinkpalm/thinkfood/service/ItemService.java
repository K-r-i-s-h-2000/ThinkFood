/**
 * Service interface for managing items.
 * This interface defines methods for creating, retrieving, updating, and deleting items.
 *
 * @author agrah.mv
 * @since 27 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.CategoryNotFoundException;
import com.thinkpalm.thinkfood.exception.ItemDetailsMissingException;
import com.thinkpalm.thinkfood.exception.ItemNotFoundException;
import com.thinkpalm.thinkfood.model.Item;
import org.springframework.data.domain.Page;


import java.util.List;

/**
 * Service interface for managing items.
 */
public interface ItemService {
    /**
     * Creates a new item and associates it with the specified category.
     *
     * @param item       The Item object containing details of the item.
     * @param categoryId The ID of the category to which the item belongs.
     * @return The created Item object.
     * @throws ItemDetailsMissingException If details of the item are missing.
     * @throws CategoryNotFoundException   If the specified category is not found.
     */
    Item createItem(Item item, Long categoryId)
            throws ItemDetailsMissingException, CategoryNotFoundException;

    /**
     * Retrieves all items belonging to a specific category.
     *
     * @param categoryId The ID of the category.
     * @return List of items belonging to the specified category.
     */
    List<Item> getAllItemsByCategory(Long categoryId);

    /**
     * Retrieves a paginated list of items belonging to a specific category.
     *
     * @param categoryId The ID of the category.
     * @param page       The page number.
     * @param pageSize   The number of items per page.
     * @return Paginated list of items belonging to the specified category.
     */
    Page<Item> getAllItemsByCategoryPagination(Long categoryId, Integer page, Integer pageSize);

    /**
     * Deletes an item by its ID.
     *
     * @param id The ID of the item to delete.
     * @throws ItemNotFoundException If the item with the specified ID is not found.
     */
    void deleteItemById(Long id) throws ItemNotFoundException;

    /**
     * Soft deletes an item by marking it as inactive.
     *
     * @param id The ID of the item to soft delete.
     * @return A message indicating the success of the soft deletion.
     * @throws ItemNotFoundException If the item with the specified ID is not found.
     */
    String softDeleteItemById(Long id) throws ItemNotFoundException;

    /**
     * Updates the details of an existing item identified by its ID.
     *
     * @param id The ID of the item to update.
     * @param updatedItem The updated item object with new details.
     * @return The updated Item object representing the modified item.
     * @throws ItemNotFoundException If the item with the specified ID is not found.
     */
    Item updateItemById(Long id, Item updatedItem)
            throws ItemNotFoundException;
}

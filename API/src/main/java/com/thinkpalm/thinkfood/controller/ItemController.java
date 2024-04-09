/**
 * Controller for managing item operations.
 *
 * This controller handles various operations related to items, including creating, retrieving,
 * updating, and deleting items. It also provides methods for retrieving items by category,
 * paginated item retrieval, and both soft and permanent deletion of items.
 *
 * @author agrah.mv
 * @since 27 October 2023
 * @version 2.0
 */
package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.CategoryNotFoundException;
import com.thinkpalm.thinkfood.exception.ItemDetailsMissingException;
import com.thinkpalm.thinkfood.exception.ItemNotFoundException;
import com.thinkpalm.thinkfood.model.Item;
import com.thinkpalm.thinkfood.service.ItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/think-food/item")
@SecurityRequirement(name="thinkfood")
public class ItemController {

    @Autowired
    private ItemService itemService;


    /**
     * Create a new item.
     *
     * @param item       The item details to create.
     * @param categoryId The ID of the category to which the item belongs.
     * @return A response entity containing the created item details.
     */
    @PostMapping("/category-id/{categoryId}/add-item")
    public ResponseEntity<Object> createItem(@RequestBody Item item, @PathVariable Long categoryId) {
        try {
            log.info("Entered into createItem method");
            Item createdItem = itemService.createItem(item, categoryId);
            return ResponseEntity.ok(createdItem);
        } catch (ItemDetailsMissingException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * Get all items by category.
     *
     * @param categoryId The ID of the category to retrieve items from.
     * @return A response entity containing a list of items in the specified category.
     */
    @GetMapping("/get-by-category/{categoryId}")
    public ResponseEntity<Object> getAllItemsByCategory(@PathVariable Long categoryId) {
        try {
            log.info("Entered into getAllItemsByCategory method");
            List<com.thinkpalm.thinkfood.model.Item> items = itemService.getAllItemsByCategory(categoryId);
            return ResponseEntity.ok(items);
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }


    /**
     * Get all items by category with pagination.
     *
     * @param categoryId The ID of the category to retrieve items from.
     * @param page       The page number for pagination.
     * @param pageSize   The number of items per page.
     * @return A response entity containing a paginated list of items in the specified category.
     */
    @GetMapping("/{categoryId}/{page}/{pageSize}")
    public ResponseEntity<Object> getAllItemsByCategoryPagination(
            @PathVariable Long categoryId,
            @PathVariable Integer page,
            @PathVariable Integer pageSize
    ) {
        try {
            log.info("Entered into getAllItemsByCategoryPagination method");
            Page<com.thinkpalm.thinkfood.model.Item> items = itemService.getAllItemsByCategoryPagination(categoryId, page, pageSize);
            return ResponseEntity.ok(items);
        } catch (CategoryNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }



    /**
     * Soft delete an item by ID.
     *
     * @param id The ID of the item to be soft-deleted.
     * @return A message indicating the success or failure of the soft delete operation.
     */
    @DeleteMapping("/soft-delete/{id}")
    public Boolean softDeleteItemById(@PathVariable Long id) {
        try {
            log.info("Entered into softDeleteItemById method");
            itemService.softDeleteItemById(id);
            return true;
        } catch (ItemNotFoundException e) {
            log.error(e.getMessage());
            return false;
        }
    }


    /**
     * Delete an item permanently by ID.
     *
     * @param id The ID of the item to be permanently deleted.
     * @return A message indicating the success or failure of the delete operation.
     */
    @DeleteMapping("/delete-item/{id}")
    public String deleteItemById(@PathVariable("id") Long id) {
       try {
           log.info("Entered into deleteItemById method");
           itemService.deleteItemById(id);
           return "Item deleted successfully";
       }catch (ItemNotFoundException e){
           log.error(e.getMessage());
            return "Item not found with ID";
       }
    }


    /**
     * Update an item by ID.
     *
     * @param id          The ID of the item to update.
     * @param updatedItem The updated item details.
     * @return A response entity containing the updated item details.
     */
    @PutMapping("/update-item/{id}")
    public ResponseEntity<Object> updateItemById(@PathVariable Long id, @RequestBody Item updatedItem) {
        try {
            log.info("Entered into updateItemById method");
            Item updatedItemResponse = itemService.updateItemById(id, updatedItem);
            return ResponseEntity.ok(updatedItemResponse);
        } catch (ItemNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }


}

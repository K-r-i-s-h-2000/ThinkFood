/**
 * Controller for managing menu operations.
 *
 * @author Sharon Sam
 * @since 26 October 2023
 * @version 2.0
 */


package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.*;
import com.thinkpalm.thinkfood.model.Menu;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import com.thinkpalm.thinkfood.model.UpdateMenu;
import com.thinkpalm.thinkfood.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/think-food/menu")
@Log4j2
@SecurityRequirement(name="thinkfood")
public class MenuController {


    @Autowired
    private MenuService menuService;

    /**
     * Get menu by ID.
     *
     * @param id The ID of the menu to retrieve.
     * @return A response entity containing the menu details.
     */

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMenuById(@PathVariable Long id) {
        try {
            Menu menu = menuService.getMenuById(id);
            log.info("Retrieved Menu with ID " + id);
            return ResponseEntity.ok(menu);
        } catch (NotFoundException e) {
            log.error("Menu not found with ID " + id + ": " + e.getMessage());
            return ResponseEntity.ok("Menu not found with ID " + id);
        }
    }

    /**
     * Create a new menu.
     *
     * @param menu The menu details to create.
     * @return A response entity indicating the success or failure of the operation.
     */

    @PostMapping
    public Boolean createMenu(@RequestBody Menu menu) {
        try {
            menuService.createMenu(menu);
            log.info("Menu created successfully");
            return true;
        } catch (EmptyInputException e) {
            log.error("Input is empty or missing required fields: " + e.getMessage());
            return false;
        } catch (NotFoundException e) {
            log.error("Restaurant or Item not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update a menu with the specified ID.
     *
     * @param id          The ID of the menu to update.
     * @param updatedMenu The updated menu details.
     * @return A ResponseEntity indicating the success or failure of the update operation.
     */

    @PutMapping("/{id}")
    public Boolean updateMenu(@PathVariable Long id, @RequestBody UpdateMenu updatedMenu) {
        try {
            menuService.updateMenu(id, updatedMenu);
            log.info("Menu updated successfully for ID " + id);
            return true;
        } catch (NotFoundException e) {
            log.error("Menu not found with ID " + id + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a menu with the specified ID.
     *
     * @param id The ID of the menu to be deleted.
     * @return A ResponseEntity indicating the success or failure of the delete operation.
     */

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMenuById(@PathVariable Long id) {
        try {
            menuService.deleteMenuById(id);
            log.info("Menu with ID " + id + " has been deleted.");
            return ResponseEntity.ok("Menu with ID " + id + " has been deleted.");
        } catch (NotFoundException e) {
            log.error("Invalid Menu Id: " + e.getMessage());
            return ResponseEntity.ok("Invalid Menu Id");
        }
    }

    /**
     * Soft delete a menu with the specified ID.
     *
     * @param id The ID of the menu to be soft-deleted.
     * @return A ResponseEntity indicating the success or failure of the soft delete operation.
     */
    @DeleteMapping("/{id}")
    public Boolean softDeleteMenu(@PathVariable Long id) {
        try {
            String response = menuService.softDeleteMenuById(id);
            log.info("Soft delete for Menu with ID " + id + " was successful.");
            return true;
        } catch (NotFoundException e) {
            log.error("Menu not found for ID " + id + ": " + e.getMessage());
            return false;
        }
    }

}

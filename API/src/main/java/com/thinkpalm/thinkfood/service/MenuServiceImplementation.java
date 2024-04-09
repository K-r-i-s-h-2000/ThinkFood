/**
 * Service implementation for managing menus.
 * This class is responsible for handling CRUD operations on menu entities.
 *
 * @author Sharon Sam
 * @since 26 October 2023
 * @version 2.0
 **/

package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.Item;
import com.thinkpalm.thinkfood.entity.Restaurant;
import com.thinkpalm.thinkfood.exception.*;
import com.thinkpalm.thinkfood.model.Menu;
import com.thinkpalm.thinkfood.model.UpdateMenu;
import com.thinkpalm.thinkfood.repository.ItemRepository;
import com.thinkpalm.thinkfood.repository.MenuRepository;
import com.thinkpalm.thinkfood.repository.RestaurantRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Log4j2
public class MenuServiceImplementation implements MenuService{

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Retrieve a menu by its unique ID.
     *
     * @param id The ID of the menu to retrieve.
     * @return The menu with the specified ID.
     * @throws NotFoundException If the menu with the given ID is not found or if it is not active.
     */



    @Override
    public Menu getMenuById(Long id) throws NotFoundException {

        log.info("Retrieving menu with ID: " + id);

        Optional<com.thinkpalm.thinkfood.entity.Menu> menuWrapper = menuRepository.findById(id);

        if (menuWrapper.isEmpty() || !menuWrapper.get().getIsActive()) {
            throw new NotFoundException("Menu not found with id " + id);
        }

        com.thinkpalm.thinkfood.entity.Menu menuEntity = menuWrapper.get();

        Menu menuModel = new Menu();
        BeanUtils.copyProperties(menuEntity, menuModel);

        menuModel.setRestaurantId(menuEntity.getRestaurant().getId());
        menuModel.setItemId(menuEntity.getItem().getId());

        log.info("Menu with ID " + id + " retrieved successfully");

        return menuModel;
    }

    /**
     * Create a new menu.
     *
     * @param menu The menu to create.
     * @return The created menu.
     * @throws EmptyInputException If any required field is empty in the input.
     * @throws NotFoundException If the associated restaurant is not found.
     * @throws NotFoundException If the associated item is not found.
     */

    @Override
    public Menu createMenu(Menu menu) throws EmptyInputException, NotFoundException {

        log.info("Creating a new menu");

        if (menu.getRestaurantId()==null || menu.getItemId()==null|| menu.getItemDescription()==null || menu.getItemPrice()==null|| menu.getPreparationTime()==null || menu.getItemAvailability()==null ){
            throw new EmptyInputException("All Fields are Mandatory");
        }

        log.info("Input validation successful");

        com.thinkpalm.thinkfood.entity.Menu menuEntity = new com.thinkpalm.thinkfood.entity.Menu();

        Long restaurantId = menu.getRestaurantId();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new NotFoundException("Restaurant not found with ID " + restaurantId));


        if (!restaurant.getIsActive()) {
            throw new NotFoundException("Restaurant not found with ID " + restaurantId);
        }

//        Restaurant restaurant = restaurantRepository.findById(menu.getRestaurantId())
//                .filter(Restaurant::getIsActive)
//                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + menu.getRestaurantId()));

        Long itemId = menu.getItemId();


        Item item = itemRepository.findById(itemId)
                        .orElseThrow(()-> new NotFoundException("Item not found with ID " + itemId));

        if (!item.getIsActive()) {
            throw new NotFoundException("Item not found with ID " + itemId);
        }

        menuEntity.setItem(item);
        menuEntity.setRestaurant(restaurant);
        menuEntity.setItemDescription(menu.getItemDescription());
        menuEntity.setItemPrice(menu.getItemPrice());
        menuEntity.setPreparationTime(menu.getPreparationTime());
        menuEntity.setItemAvailability(menu.getItemAvailability());
        menuEntity.setIsActive(true);

        log.info("Saving the menu entity");

        menuEntity = menuRepository.save(menuEntity);

        log.info("Menu entity saved successfully");

        Menu menuModel = new Menu();

        menuModel.setRestaurantId(restaurantId);
        menuModel.setItemId(itemId);

        menuModel.setItemDescription(menuEntity.getItemDescription());
        menuModel.setItemPrice(menuEntity.getItemPrice());
        menuModel.setPreparationTime(menuEntity.getPreparationTime());
        menuModel.setItemAvailability(menuEntity.getItemAvailability());
        menuModel.setItemAvailability(menuEntity.getItemAvailability());

        log.info("Menu created successfully");

        return menuModel;
    }

    /**
     * Update an existing menu.
     *
     * @param id The ID of the menu to update.
     * @param updatedMenu The updated menu data.
     * @return The updated menu.
     * @throws NotFoundException If the menu with the given ID is not found.
     */

    @Override
    public UpdateMenu updateMenu(long id, UpdateMenu updatedMenu) throws  NotFoundException {
        com.thinkpalm.thinkfood.entity.Menu menuEntity = menuRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Menu not found with ID: "+id));

        if (!menuEntity.getIsActive()) {
            throw new NotFoundException("Menu with id " + id + " is not found");
        }

        log.info("Menu found and is active");
        log.info("Updating menu with ID: " + id);


        if (updatedMenu.getItemDescription()!=null){
            menuEntity.setItemDescription(updatedMenu.getItemDescription());
        }

        if (updatedMenu.getItemPrice()!=null){
            menuEntity.setItemPrice(updatedMenu.getItemPrice());
        }

        if (updatedMenu.getPreparationTime()!=null){
            menuEntity.setPreparationTime(updatedMenu.getPreparationTime());
        }

        if (updatedMenu.getItemAvailability()!=null){
            menuEntity.setItemAvailability(updatedMenu.getItemAvailability());
        }

        menuEntity = menuRepository.save(menuEntity);

        log.info("Menu entity updated successfully");

        UpdateMenu menuModel = new UpdateMenu();

        menuModel.setItemDescription(menuEntity.getItemDescription());
        menuModel.setItemPrice(menuEntity.getItemPrice());
        menuModel.setPreparationTime(menuEntity.getPreparationTime());
        menuModel.setItemAvailability(menuEntity.getItemAvailability());

        log.info("Menu updated successfully");

        return menuModel;
    }

    /**
     * Delete a menu by its ID.
     *
     * @param id The ID of the menu to delete.
     * @return The deleted menu.
     * @throws NotFoundException If the menu with the given ID is not found.
     */

    @Override
    public Menu deleteMenuById(Long id) throws NotFoundException {

        Optional<com.thinkpalm.thinkfood.entity.Menu> menuOptional = menuRepository.findById(id);

        if (menuOptional.isEmpty()) {
            throw new NotFoundException("Menu not found with ID: " + id);
        }


        com.thinkpalm.thinkfood.entity.Menu deletedMenu = menuOptional.get();

        log.info("Menu found for deletion");

        Menu deletedMenuModel = new Menu();
        BeanUtils.copyProperties(deletedMenu, deletedMenuModel);
        log.info("Copying menu properties to the deleted menu model");


        menuRepository.deleteById(id);
        log.info("Menu with ID " + id + " deleted successfully");

        return deletedMenuModel;
    }

    /**
     * Soft delete a menu by updating its 'is_active' status to false.
     *
     * @param id The ID of the menu to be soft-deleted.
     * @return A message indicating the success of the soft delete operation.
     * @throws NotFoundException If the menu with the given ID is not found.
     */

    @Override
    public String softDeleteMenuById(Long id) throws NotFoundException {

        com.thinkpalm.thinkfood.entity.Menu menu = menuRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Menu with ID " + id + " not found"));

        if (!menu.getIsActive()) {
            throw new NotFoundException("Menu with ID " + id + " not found");
        }

        log.info("Menu found and is active");

        menu.setIsActive(false);

        menuRepository.save(menu);
        log.info("Menu with ID " + id + " has been soft deleted");

        return "Menu With Id "+id+" deleted successfully!";

    }
}

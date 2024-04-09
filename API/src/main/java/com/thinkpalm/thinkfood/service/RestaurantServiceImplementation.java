/**
 * Service implementation for managing restaurants.
 * This class is responsible for handling CRUD operations on restaurant entities.
 * author: Sharon Sam
 * since : 26 October 2023
 * version : 2.0
 */


package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.Menu;
import com.thinkpalm.thinkfood.entity.Order;
import com.thinkpalm.thinkfood.entity.User;
import com.thinkpalm.thinkfood.exception.EmptyInputException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.entity.PreparationStatus;
import com.thinkpalm.thinkfood.model.Restaurant;
import com.thinkpalm.thinkfood.repository.MenuRepository;
import com.thinkpalm.thinkfood.repository.OrderRepository;
import com.thinkpalm.thinkfood.repository.RestaurantRepository;
import com.thinkpalm.thinkfood.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class RestaurantServiceImplementation implements RestaurantService{
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    /**
     * Retrieves a restaurant by its unique identifier.
     *
     * @param id The unique identifier of the restaurant to retrieve.
     * @return The restaurant details as a Restaurant model.
     * @throws NotFoundException if the restaurant with the specified ID is not found.
     */
    @Override
    public Restaurant getRestaurantById(Long id) throws NotFoundException {
        log.info("Getting Restaurant by ID");
        Optional<com.thinkpalm.thinkfood.entity.Restaurant> restaurantWrapper = restaurantRepository.findById(id);

        if (restaurantWrapper.isEmpty()) {
            throw new NotFoundException("Restaurant not found with id " + id);
        }

        com.thinkpalm.thinkfood.entity.Restaurant restaurantEntity = restaurantWrapper.get();


        if (!restaurantEntity.getIsActive()) {
            throw new NotFoundException("Restaurant with id " + id + " is not found");
        }

        Restaurant restaurantModel = new Restaurant();
        BeanUtils.copyProperties(restaurantEntity, restaurantModel);

        log.info("Retrieved Restaurant with ID {}",id);

        return restaurantModel;
    }



    /**
     * Updates the preparation status of an order identified by its unique ID.
     *
     * @param orderId The unique identifier of the order to update its preparation status.
     * @return A status message indicating the result of the update, e.g., "Status Updated" or "Invalid Preparation Status."
     * @throws NotFoundException if the order with the specified ID is not found.
     */

    @Override
    public String updatePreparationStatus(Long orderId) throws NotFoundException {
        log.info("Getting Order ID");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId));

        String currentStatus = order.getPreparationStatus();

        log.info("Current Preparation Status for Order {}: {}", orderId, currentStatus);

        if (String.valueOf(PreparationStatus.PREPARATION).equals(currentStatus)) {
            order.setPreparationStatus(String.valueOf(PreparationStatus.OUT_FOR_DELIVERY));
        }

        orderRepository.save(order);

        log.info("Status Updated for Order {}",orderId);

        return "Status Updated";
    }


    /**
     * Creates a new restaurant entity based on the provided restaurant information.
     *
     * @param restaurant The restaurant object containing details to be created.
     * @return A new restaurant object representing the created restaurant.
     * @throws EmptyInputException if any of the required fields in the provided restaurant object is missing or empty.
     */

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) throws EmptyInputException {

        log.info("Generating new restaurant");

        if (restaurant.getRestaurantName()==null || restaurant.getRestaurantDescription()==null || restaurant.getRestaurantLatitude()==null || restaurant.getRestaurantLongitude()==null
                || restaurant.getPhoneNumber()==null || restaurant.getEmail()==null || restaurant.getRestaurantAvailability()==null){
            throw new EmptyInputException("All Fields are Mandatory");
        }

        log.info("Creating restaurant entity");

        com.thinkpalm.thinkfood.entity.Restaurant restaurantEntity = new com.thinkpalm.thinkfood.entity.Restaurant();

        restaurantEntity.setRestaurantName(restaurant.getRestaurantName());
        restaurantEntity.setRestaurantDescription(restaurant.getRestaurantDescription());
        restaurantEntity.setRestaurantLatitude(restaurant.getRestaurantLatitude());
        restaurantEntity.setRestaurantLongitude(restaurant.getRestaurantLongitude());
        restaurantEntity.setPhoneNumber(restaurant.getPhoneNumber());
        restaurantEntity.setEmail(restaurant.getEmail());
        restaurant.setRestaurantAvailability(restaurant.getRestaurantAvailability());
        restaurantEntity.setIsActive(true);

        log.info("Saving restaurant entity to database");
        restaurantEntity =  restaurantRepository.save( restaurantEntity);

        Restaurant restaurantModel = new Restaurant();

        log.info("Generating Restaurant Model");
        restaurantModel.setRestaurantName(restaurantEntity.getRestaurantName());
        restaurantModel.setRestaurantDescription(restaurantEntity.getRestaurantDescription());
        restaurantModel.setRestaurantLatitude(restaurantEntity.getRestaurantLatitude());
        restaurantModel.setRestaurantLongitude(restaurantEntity.getRestaurantLongitude());
        restaurantModel.setPhoneNumber(restaurantEntity.getPhoneNumber());
        restaurantModel.setEmail(restaurantEntity.getEmail());
        restaurantModel.setRestaurantAvailability(restaurantEntity.isRestaurantAvailability());

        log.info("Restaurant created successfully");

        return restaurantModel;
    }

    /**
     * Updates the details of an existing restaurant identified by its unique ID.
     *
     * @param id The unique identifier of the restaurant to update.
     * @param updatedRestaurant The updated restaurant object with new details.
     * @return The updated restaurant object representing the modified restaurant.
     * @throws NotFoundException if the restaurant with the specified ID is not found.
     */

    @Override
    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) throws NotFoundException {
        log.info("Retrieving restaurant id");
        com.thinkpalm.thinkfood.entity.Restaurant restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found with ID:"+id));

        if (!restaurantEntity.getIsActive()) {
            throw new NotFoundException("Restaurant with id " + id + " is not found");
        }

        log.info("Retrieved restaurant entity for updating");

        if (updatedRestaurant.getRestaurantName()!=null){
            restaurantEntity.setRestaurantName(updatedRestaurant.getRestaurantName());
        }

        if (updatedRestaurant.getRestaurantDescription()!=null){
            restaurantEntity.setRestaurantDescription(updatedRestaurant.getRestaurantDescription());
        }


        if (updatedRestaurant.getRestaurantLatitude()!=null){
            restaurantEntity.setRestaurantLatitude(updatedRestaurant.getRestaurantLatitude());
        }

        if (updatedRestaurant.getRestaurantLongitude()!=null){
            restaurantEntity.setRestaurantLongitude(updatedRestaurant.getRestaurantLongitude());
        }

        if (updatedRestaurant.getPhoneNumber()!=null){
            restaurantEntity.setPhoneNumber(updatedRestaurant.getPhoneNumber());
        }

        if (updatedRestaurant.getEmail()!=null){
            restaurantEntity.setEmail(updatedRestaurant.getEmail());
        }

        if (updatedRestaurant.getRestaurantAvailability()!=null) {
            restaurantEntity.setRestaurantAvailability(updatedRestaurant.getRestaurantAvailability());
        }

        restaurantEntity = restaurantRepository.save(restaurantEntity);

        Restaurant restaurantModel = new Restaurant();

        restaurantModel.setRestaurantName(restaurantEntity.getRestaurantName());
        restaurantModel.setRestaurantDescription(restaurantEntity.getRestaurantDescription());
        restaurantModel.setRestaurantLatitude(restaurantEntity.getRestaurantLatitude());
        restaurantModel.setRestaurantLongitude(restaurantEntity.getRestaurantLongitude());
        restaurantModel.setPhoneNumber(restaurantEntity.getPhoneNumber());
        restaurantModel.setEmail(restaurantEntity.getEmail());
        restaurantModel.setRestaurantAvailability(restaurantEntity.isRestaurantAvailability());

        log.info("Restaurant updated successfully");

        return restaurantModel;

    }


    /**
     * Deletes a restaurant entity based on its unique ID and related data.
     *
     * @param id The unique identifier of the restaurant to be deleted.
     * @return A restaurant object representing the deleted restaurant.
     * @throws NotFoundException if the restaurant with the specified ID is not found.
     */

    @Override
    public Restaurant deleteRestaurantById(Long id) throws NotFoundException {

        Optional<com.thinkpalm.thinkfood.entity.Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            throw new NotFoundException("Restaurant not found with ID: " + id);
        }

        com.thinkpalm.thinkfood.entity.Restaurant deletedRestaurant = restaurantOptional.get();

        List<Menu> relatedMenus = menuRepository.findByRestaurantId(id);

        if (!relatedMenus.isEmpty()) {
            for (Menu menu : relatedMenus) {
                log.info("Deleting related menu with ID: " + menu.getId());
                menuRepository.delete(menu);
            }
        }


        User associatedUser = deletedRestaurant.getUser();

        if (associatedUser != null) {
            log.info("Deleting associated user with ID: " + associatedUser.getId());
            userRepository.delete(associatedUser);
        }

        Restaurant deletedRestaurantModel = new Restaurant();
        BeanUtils.copyProperties(deletedRestaurant, deletedRestaurantModel);

        log.info("Restaurant deleted successfully");

        restaurantRepository.deleteById(id);

        return deletedRestaurantModel;
    }



    @Override
    public Boolean softDeleteRestaurantById(Long id) throws NotFoundException {
        log.info("Retrieving ID: " + id+" for soft deleting");
        com.thinkpalm.thinkfood.entity.Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with ID " + id + " not found"));

        restaurant.setIsActive(false);
        restaurantRepository.save(restaurant);

        List<Menu> menus = menuRepository.findByRestaurantId(id);
        for (Menu menu : menus) {
            log.info("Soft deleting menu with ID: " + menu.getId());
            menu.setIsActive(false);
        }
        menuRepository.saveAll(menus);

        User user =restaurant.getUser();
        if (user != null) {
            log.info("Soft deleting user with ID: " + user.getId());
            user.setIsActive(false);
            userRepository.save(user);
        }

        return true;
    }

}

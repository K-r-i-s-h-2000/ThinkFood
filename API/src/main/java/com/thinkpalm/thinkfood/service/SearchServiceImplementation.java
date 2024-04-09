/**
 * Service class for search requests
 * This class contains abstract functions to perform  search and read information to the corresponding request.
 *
 * @author: Rinkle Rose Renny
 * @version : 2.0
 * @since : 31 October 2023
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.model.Menu;
import com.thinkpalm.thinkfood.exception.*;
import com.thinkpalm.thinkfood.model.Item;
import com.thinkpalm.thinkfood.model.Restaurant;
import com.thinkpalm.thinkfood.model.Search;
import com.thinkpalm.thinkfood.repository.ItemRepository;
import com.thinkpalm.thinkfood.repository.MenuRepository;
import com.thinkpalm.thinkfood.repository.RestaurantRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Log4j2
@Service
public class SearchServiceImplementation implements SearchService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;


    /**
     * This function will list all items that required from the table.
     * @param pageNo contain Number in which it should start display.
     * @param pageSize contains value of number of records that need to be displayed.
     * @return list of strings that contain item names.
     */
    @Override
    public List<Item> listAllItems(Integer pageNo, Integer pageSize) {
        List<Item> itemModelList = new ArrayList<>();
        log.info("Displaying all items starting from {} with size{}", pageNo, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("itemName"));
        try {
            Page<com.thinkpalm.thinkfood.entity.Item> itemEntity = itemRepository.findAll(pageRequest);
            itemEntity.forEach(item -> {
                Item itemModel = new Item();
                if ((item.getIsActive()) && (!itemEntity.isEmpty())) {
                    itemModel.setId(item.getId());
                    itemModel.setItemName(item.getItemName());
                    itemModel.setImage(item.getImage());
                    itemModelList.add(itemModel);
                }
            });
        }
        catch (Exception e)
        {
            throw new DetailsMissingException("Item table contain invalid values");
        }
        log.info("Retrieve {} item details", itemModelList.size());
        return itemModelList;
    }

    /**
     * This functions find items from all the restaurants along with price.
     * @param itemId contains id of the specified item name.
     * @return list of specified item with restaurant and price.
     */

    @Override
    public List<Search> listSpecifiedItem(Long itemId) {
        log.info("Displaying all items with that specified id {} ", itemId);
        Item itemModel = new Item();
        Optional<com.thinkpalm.thinkfood.entity.Item> itemEntity;
        try {
            itemEntity = itemRepository.findById(itemId);
        }
        catch (Exception e)
        {
            log.error("Null values in Item table");
            throw new DetailsMissingException("Item table contain null values");
        }
            if (itemEntity.isPresent() && itemEntity.get().getIsActive()) {
                log.info("Item is present in table");
                log.info("Item is present with id {}", itemEntity.get().getId());
                itemModel.setItemName(itemEntity.get().getItemName());
                itemModel.setId(itemEntity.get().getId());
            } else {
                log.error("Item is not found with id {}", itemId);
                throw new DetailsNotFoundException("Items Details not found exception");
            }
        List<Search> searchModelList = new ArrayList<>();
        List<com.thinkpalm.thinkfood.entity.Menu> menuEntity;
       try {
           menuEntity = menuRepository.findAllByitemId(itemModel.getId());
       }
       catch(Exception e)
       {
           log.error("Null values in Menu table");
           throw new DetailsMissingException("Null Values are  entered in Menu table");
       }
           for (com.thinkpalm.thinkfood.entity.Menu menu : menuEntity) {
               if ((menu.getIsActive()) && (!menuEntity.isEmpty())) {
                   if(menu.getItemAvailability()) {
                       log.info("Item is present in Menu table");
                       Search searchModel = new Search();

                           Optional<com.thinkpalm.thinkfood.entity.Restaurant> restaurantEntity;
                           restaurantEntity = restaurantRepository.findById(menu.getRestaurant().getId());

                           if (restaurantEntity.isPresent() && restaurantEntity.get().getIsActive()) {
                               log.info("Restaurant is present");
                               log.info("Restaurant is present with id{}", restaurantEntity.get().getId());
                               searchModel.setRestaurantName(restaurantEntity.get().getRestaurantName());
                               searchModel.setRestaurantId(restaurantEntity.get().getId());
                               searchModel.setRestaurantImage(restaurantEntity.get().getImage());
                               searchModel.setRestaurantDescription(restaurantEntity.get().getRestaurantDescription());
                               searchModel.setRestaurantEmail(restaurantEntity.get().getEmail());
                               searchModel.setRestaurantPhoneNumber(restaurantEntity.get().getPhoneNumber());
                           }
                           else
                           {
                               log.error("Restaurant Details are not found");
                               throw new DetailsNotFoundException("Restaurant is not available.");
                           }
                       searchModel.setItemId(itemModel.getId());
                       searchModel.setItemName(itemModel.getItemName());
                       searchModel.setItemPrice(menu.getItemPrice());
                       searchModelList.add(searchModel);
                   }
               }
           }
        log.info("Retrieved information of {} items ", searchModelList.size());
        return searchModelList;
    }

    /**
     * This functions find restaurant and display all item present in that restaurant with price..
     * @param restaurantId contains id of the restaurant name.
     * @param pageNo contain Number in which it should start display.
     * @param pageSize contains value of number of records that need to be displayed.
     * @return list of items under that specified restaurant.
     */
    @Override
    public List<Search> listAllItemsRestaurant(Long restaurantId, Integer pageNo, Integer pageSize) {
        log.info("Displaying all items in that restaurant of id {} starting from {} with size{}", restaurantId, pageNo, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Optional<com.thinkpalm.thinkfood.entity.Restaurant> restaurantEntity;
        try {
            restaurantEntity = restaurantRepository.findById(restaurantId);
        } catch (Exception e) {
            throw new DetailsMissingException("Null Values entered in restaurant table.");
        }
        Restaurant restaurantModel = new Restaurant();
        if (restaurantEntity.isPresent() && restaurantEntity.get().getIsActive()) {
            log.info("Restaurant is found");
            restaurantModel.setRestaurantName(restaurantEntity.get().getRestaurantName());
            restaurantModel.setId(restaurantEntity.get().getId());

        } else {
            log.error("Restaurant is not found with id{}", restaurantId);
            throw new DetailsNotFoundException("Restaurant Details Not Found.  ");
        }
        List<Search> searchModelList = new ArrayList<>();
        Page<com.thinkpalm.thinkfood.entity.Menu> menuEntity;
        try {
            menuEntity = menuRepository.findAllByrestaurantId(restaurantModel.getId(), pageRequest);
        } catch (Exception e) {
            throw new DetailsMissingException("Null Values entered in Menu table.");
        }

        for (com.thinkpalm.thinkfood.entity.Menu menu : menuEntity) {
            if ((menu.getIsActive()) && (!menuEntity.isEmpty())) {
                Search searchModel = new Search();
                log.info("Menu is found");
                Optional<com.thinkpalm.thinkfood.entity.Item> itemEntity;
                try {
                    itemEntity = itemRepository.findById(menu.getItem().getId());

                } catch (Exception e) {
                    throw new DetailsMissingException("Null value in Item table");
                }
                if (itemEntity.isPresent() && itemEntity.get().getIsActive()) {
                    log.info("Item is found with id {}", itemEntity.get().getId());
                    searchModel.setItemName(itemEntity.get().getItemName());
                    searchModel.setItemId(itemEntity.get().getId());
                    searchModel.setImage(itemEntity.get().getImage());

                } else {
                    log.error("Item Not Found with id {}", itemEntity.get().getId());
                    throw new DetailsNotFoundException("Item Details are not Found.");
                }
                searchModel.setMenuId(menu.getId());
                searchModel.setItemDescription(menu.getItemDescription());
                searchModel.setPreparationTime(menu.getPreparationTime());
                searchModel.setItemAvailability(menu.getItemAvailability());
                searchModel.setItemPrice(menu.getItemPrice());
                searchModel.setRestaurantId(restaurantModel.getId());
                searchModel.setRestaurantName(restaurantModel.getRestaurantName());
                searchModel.setImage(searchModel.getImage());
                searchModelList.add(searchModel);

            }
        }
        log.info("Retrieve list of item of size{}", searchModelList.size());
        return searchModelList;
    }

    /**
     * This function will list all required that required from the table.
     * @param pageNo contain Number in which it should start display.
     * @param pageSize contains value of number of records that need to be displayed.
     * @return list of strings that contain restaurant names.
     */
    @Override
    public List<Restaurant> listAllRestaurants(Integer pageNo, Integer pageSize) {
        log.info("Displaying all restaurants starting from {} with size{}", pageNo, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<com.thinkpalm.thinkfood.entity.Restaurant> restaurantEntity;
        List<Restaurant> restaurantModelList = new ArrayList<>();
        try{
            restaurantEntity = restaurantRepository.findAll(pageRequest);
        restaurantEntity.forEach(restaurant -> {
            if ((restaurant.getIsActive())&&(!restaurantEntity.isEmpty())) {
                log.info("Restaurant is found");
                Restaurant restaurantModel = new Restaurant();
                restaurantModel.setId(restaurant.getId());
                restaurantModel.setRestaurantName(restaurant.getRestaurantName());
                restaurantModel.setRestaurantAvailability(restaurant.isRestaurantAvailability());
                restaurantModel.setRestaurantDescription(restaurant.getRestaurantDescription());
                restaurantModel.setRestaurantLatitude(restaurant.getRestaurantLatitude());
                restaurantModel.setRestaurantLongitude(restaurant.getRestaurantLongitude());
                restaurantModel.setEmail(restaurant.getEmail());
                restaurantModel.setPhoneNumber(restaurant.getPhoneNumber());
                restaurantModel.setImage(restaurant.getImage());
                restaurantModelList.add(restaurantModel);
            }
        });
        }
        catch(Exception e)
        {
            throw new DetailsMissingException("Null values in table restaurant");
        }
        log.info("Retrieve the restaurant details of size{}", restaurantModelList.size());
        return restaurantModelList;
    }

    /**
     * This function would provide list of restaurants present around the location entered.
     * @param latitude latitude of the position entered.
     * @param longitude longitude of the position entered.
     * @return list of restaurant.
     */

    public List<Restaurant> listAllRestaurantLocation(Double latitude, Double longitude) {
        log.info("Displaying all restaurants within the specified latitude{} and longitude{}.", latitude, longitude);
        double calculatedLatitude = latitude + 25;
        double calculatedlongitude = longitude + 25;
        List<Restaurant> restaurantModelList = new ArrayList<>();
        List<com.thinkpalm.thinkfood.entity.Restaurant> restaurantEntity;
        try{
            restaurantEntity= restaurantRepository.findAll();
        restaurantEntity.forEach(restaurant -> {
            if ((restaurant.getIsActive())&&(!restaurantEntity.isEmpty())) {
                log.info("Restaurant is found");
                if ((restaurant.getRestaurantLatitude() < calculatedLatitude) && (restaurant.getRestaurantLongitude() < calculatedlongitude)) {
                    Restaurant restaurantModel = new Restaurant();
                    restaurantModel.setId(restaurant.getId());
                    restaurantModel.setRestaurantName(restaurant.getRestaurantName());
                    restaurantModel.setRestaurantAvailability(restaurant.isRestaurantAvailability());
                    restaurantModel.setRestaurantDescription(restaurant.getRestaurantDescription());
                    restaurantModel.setRestaurantLatitude(restaurant.getRestaurantLatitude());
                    restaurantModel.setRestaurantLongitude(restaurant.getRestaurantLongitude());
                    restaurantModel.setEmail(restaurant.getEmail());
                    restaurantModel.setPhoneNumber(restaurant.getPhoneNumber());
                    restaurantModelList.add(restaurantModel);
                }
            }
        });
        }
        catch (Exception e)
        {
            throw new DetailsMissingException("Null values in Restaurant table");
        }
        log.info("Retrieve information about restaurants of size {}", restaurantModelList.size());
        return restaurantModelList;
    }

    /**
     * This function would provide list of items present around the location entered.
     * @param latitude latitude of the position entered.
     * @param longitude longitude of the position entered.
     * @return list of item names.
     */

    @Override
    public List<Item> listAllItemsLocation(Double latitude, Double longitude) {
        log.info("Displaying all items within that specified latitude{} and longitude{} ", latitude, longitude);
        double calculatedLatitude = latitude + 25;
        double calculatedlongitude = longitude + 25;
        List<Item> searchModelList = new ArrayList<>();
        List<com.thinkpalm.thinkfood.entity.Restaurant> restaurantEntity;
        try{
            restaurantEntity = restaurantRepository.findAll();
        }
        catch (Exception e)
        {
            throw new DetailsMissingException("Null values in Restaurant table");
        }
        restaurantEntity.forEach(restaurant -> {
            if ((restaurant.getIsActive())&&(!restaurantEntity.isEmpty())) {
                log.info("Restaurant is found with id {} ", restaurant.getId());
                if ((restaurant.getRestaurantLatitude() < calculatedLatitude) && (restaurant.getRestaurantLongitude() < calculatedlongitude)) {
                    List<com.thinkpalm.thinkfood.entity.Menu> menuEntity = menuRepository.findByRestaurantId(restaurant.getId());
                    menuEntity.forEach(menu ->
                    {
                        if ((menu.getIsActive())&&(!menuEntity.isEmpty())) {
                            log.info("Menu is found with id {}", menu.getId());
                            Optional<com.thinkpalm.thinkfood.entity.Item> itemEntity;
                            try {
                                itemEntity = itemRepository.findById(menu.getItem().getId());
                            } catch (Exception e) {
                                throw new DetailsMissingException("Null values in Item table");
                            }
                            if (itemEntity.isPresent()) {
                                if (itemEntity.get().getIsActive()) {
                                    Item searchModel = new Item();
                                    searchModel.setItemName(itemEntity.get().getItemName());
                                    searchModel.setId(itemEntity.get().getId());
                                    searchModelList.add(searchModel);
                                }
                            }
                        }
                    });
                }
            }
        });
        log.info("Retrieve details of {} items", searchModelList.size());
        return searchModelList;
    }
}





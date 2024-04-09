/**
 *Controller class for get operations.
 * This class is to receive and send requests to and from users to perform corresponding get operations defined in service implementation.
 * @author: Rinkle Rose Renny
 * @since : 31 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.ItemNotFoundException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.exception.RestaurantNotFoundException;
import com.thinkpalm.thinkfood.model.Item;
import com.thinkpalm.thinkfood.model.Restaurant;
import com.thinkpalm.thinkfood.model.Search;
import com.thinkpalm.thinkfood.service.SearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@RestController
@RequestMapping("/think-food/search")
@SecurityRequirement(name="thinkfood")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * This request is to  list all items that required in the table.
     * @param pageNo contain Number in which it should start display.
     * @param pageSize contains value of number of records that need to be displayed.
     * @return list of strings that contain item names.
     */
    @GetMapping("/list-all-items")
    public List<Item> listAllItems( @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "30") Integer pageSize) {
        log.info("Received request to list all items starting from pageNo{} with size pageSize{}", pageNo,pageSize);
        List<Item> items=searchService.listAllItems( pageNo, pageSize);
        log.info("Retrieved size{} items",items.size());
        return items;
    }

    /**
     * This request is to get  items from all the restaurants along with price.
     * @param itemId contains id of the specified item name.
     * @param pageNo contain Number in which it should start display.
     * @param pageSize contains value of number of records that need to be displayed.
     * @return list of specified item with restaurant and price.
     */
    @GetMapping("/specific-item/{itemId}")
    public List<Search> listSpecificItem(@PathVariable Long itemId){
        log.info("Request to list items with the specific id{}",itemId);
        List<Search> items=new ArrayList<>();
        items=searchService.listSpecifiedItem(itemId);
        log.info("Retrieved {} items", items.size());
        return items;

    }

    /**
     * This functions request to find restaurant and display all item present in that restaurant with price..
     * @param restaurantId contains id of the restaurant.
     * @param pageNo contain Number in which it should start display.
     * @param pageSize contains value of number of records that need to be displayed.
     * @return list of items under that specified restaurant.
     */
    @GetMapping("/specific-restaurant/{restaurantId}")
    public List<Search> listItemsUnderRestaurant(@PathVariable Long restaurantId, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "30") Integer pageSize)
    {
        log.info("Request to list items with that specific restaurant with the specific id{} starting from pageNo{} with size pageSize{}",restaurantId,pageNo,pageSize);
        List<Search> items=searchService.listAllItemsRestaurant(restaurantId,pageNo,pageSize);
        log.info("Retrieved {} items", items.size());
        return items;
    }
    /**
     * This request is to  list all required that required from the table.
     * @param pageNo contain Number in which it should start display.
     * @param pageSize contains value of number of records that need to be displayed.
     * @return list of strings that contain restaurant names.
     */
    @GetMapping("/list-all-restaurants")
    public List<Restaurant> listAllRestaurants(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "30") Integer pageSize)
    {
        log.info("Request to list items all restaurants starting from pageNo{} with size pageSize{}",pageNo,pageSize);
        List<Restaurant> restaurant=searchService.listAllRestaurants(pageNo,pageSize);
        log.info("Retrieved {} restaurants", restaurant.size());
        return restaurant;
    }

    /**
     * This is to request to provide list of restaurants present around the location entered.
     * @param latitude latitude of the position entered.
     * @param longitude longitude of the position entered.
     * @return list of restaurant.
     */
    @GetMapping("/list-all-restaurants-location")
    public List<Restaurant> listAllRestaurantsLocation(@RequestParam Double latitude, @RequestParam Double longitude)
    {
        log.info("Request to list  all restaurants in specific latitude {} and longitude {}",latitude,longitude);
        List<Restaurant> restaurant=searchService.listAllRestaurantLocation(latitude,longitude);
        log.info("Retrieved {} restaurants", restaurant.size());
        return restaurant;
    }

    /**
     * This is to request to provide list of items present around the location entered.
     * @param latitude latitude of the position entered.
     * @param longitude longitude of the position entered.
     * @return list of item names.
     */
    @GetMapping("/list-all-items-location")
    public List<Item> listAllItemsLocation(@RequestParam Double latitude, @RequestParam Double longitude)
    {
        log.info("Request to list all items around that specific latitude and longitude");
        List<Item> items=searchService.listAllItemsLocation(latitude,longitude);
        log.info("Retrieved {} items", items.size());
        return items;
    }
}

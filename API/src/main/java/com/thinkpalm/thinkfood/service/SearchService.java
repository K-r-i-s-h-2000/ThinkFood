/**
 * Service interface for search requests
 * This interface contains abstract functions to perform  search and read information to the corresponding request.
 * @author: Rinkle Rose Renny
 * @since : 31 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.service;
import com.thinkpalm.thinkfood.exception.ItemNotFoundException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.exception.RestaurantNotFoundException;
import com.thinkpalm.thinkfood.model.Item;
import com.thinkpalm.thinkfood.model.Restaurant;
import com.thinkpalm.thinkfood.model.Search;

import java.util.List;

public interface SearchService {

    /**
     * This function will list all items that required in the table.
     * @param pageNo contain Number in which it should start display.
     * @param pageSize contains value of number of records that need to be displayed.
     * @return list of strings that contain item names.
     */
    public List<Item> listAllItems( Integer pageNo, Integer pageSize);

    /**
     * This functions find items from all the restaurants along with price.
     * @param itemId contains id of the specified item name.
     * @return list of specified item with restaurant and price.
     */

    public List<Search> listSpecifiedItem(Long itemId)  ;
    /**
     * This functions find restaurant and display all item present in that restaurant with price.
     * @param restaurantId contains id of the restaurant.
     * @param pageNo contain Number in which it should start display.
     * @param pageSize contains value of number of records that need to be displayed.
     * @return list of items under that specified restaurant.
     */
    public List<Search> listAllItemsRestaurant(Long restaurantId,Integer pageNo, Integer pageSize);

    /**
     * This function will list all required that required from the table.
     * @param pageNo contain Number in which it should start display.
     * @param pageSize contains value of number of records that need to be displayed.
     * @return list of strings that contain restaurant names.
     */
    public List<Restaurant> listAllRestaurants(Integer pageNo, Integer pageSize);

    /**
     * This function would provide list of restaurants present around the location entered.
     * @param latitude latitude of the position entered.
     * @param longitude longitude of the position entered.
     * @return list of restaurant.
     */
    public List<Restaurant> listAllRestaurantLocation(Double latitude, Double longitude);

    /**
     * This function would provide list of items present around the location entered.
     * @param latitude latitude of the position entered.
     * @param longitude longitude of the position entered.
     * @return list of item names.
     */
    public List<Item> listAllItemsLocation(Double latitude, Double longitude);
}

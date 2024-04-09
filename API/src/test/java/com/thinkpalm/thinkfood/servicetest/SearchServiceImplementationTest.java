///**
// * SearchService Test class contain test cases for each method in search service implementation class.
// * @author: Rinkle Rose Renny
// * @version : 2.0
// * @since : 31 October 2023
// */
//package com.thinkpalm.thinkfood.servicetest;
//
//import com.thinkpalm.thinkfood.entity.*;
//import com.thinkpalm.thinkfood.exception.DetailsMissingException;
//import com.thinkpalm.thinkfood.exception.DetailsNotFoundException;
//import com.thinkpalm.thinkfood.model.Search;
//import com.thinkpalm.thinkfood.repository.ItemRepository;
//import com.thinkpalm.thinkfood.repository.MenuRepository;
//import com.thinkpalm.thinkfood.repository.RestaurantRepository;
//import com.thinkpalm.thinkfood.service.SearchServiceImplementation;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//
//public class SearchServiceImplementationTest {
//
//    @Mock
//    private ItemRepository itemRepository;
//
//    @Mock
//    private MenuRepository menuRepository;
//
//    @Mock
//    private RestaurantRepository restaurantRepository;
//
//    @InjectMocks
//    private SearchServiceImplementation searchService;
//
//    /**
//     * This test case to list all items and returning the desired output.
//     */
//    @Test
//    public void listAllItemsTest() {
//        Category category1 = new Category("Sandwich", true);
//        Category category2 = new Category("icecream", true);
//        Item item1 = new Item(category1, "Chicken Sandwich", true);
//        Item item2 = new Item(category2, "Chocolate Icecream", true);
//        List<Item> itemEntityList = new ArrayList<>();
//        itemEntityList.add(0, item1);
//        itemEntityList.add(1, item2);
//        when(itemRepository.findAll(PageRequest.of(0, 5, Sort.by("itemName"))))
//                .thenReturn(new PageImpl<>(itemEntityList));
//        List<com.thinkpalm.thinkfood.model.Item> itemModel = searchService.listAllItems(0, 5);
//        assertEquals(2, itemModel.size());
//        assertNotNull(itemModel);
//        assertEquals("Chicken Sandwich", itemModel.get(0).getItemName());
//        assertEquals("Chocolate Icecream", itemModel.get(1).getItemName());
//
//    }
//
//    /**
//     * This test method is to test whether any of the items have any null value or is connected to any table which contains null values.
//     */
//    @Test
//    public void listAllItemsWhenNullValueEntered()
//    {
//        List<Item> itemEntityList = new ArrayList<>();
//        when(itemRepository.findAll(PageRequest.of(0, 5, Sort.by("itemName"))))
//                .thenReturn(null);
//        assertThrows(DetailsMissingException.class,()->searchService.listAllItems(0,5));
//    }
////
////    /**
////     * This test case is to list a specified item with all restaurants which has the item.
////     * It checks it gives the desired output as expected.
////     */
////    @Test
////    public void listSpecifiedItemTest()
////    {
////        PageRequest pageRequest=PageRequest.of(0,5);
////
////        Item item=new Item();
////        item.setId(1L);
////        item.setItemName("Juice");
////        item.setIsActive(true);
////
////
////        Restaurant restaurant1 =new Restaurant();
////        restaurant1.setId(1L);
////        restaurant1.setRestaurantName("Paradise");
////        restaurant1.setIsActive(true);
////        restaurant1.setRestaurantAvailability(true);
////
////        Restaurant restaurant2 =new Restaurant();
////        restaurant2.setId(2L);
////        restaurant2.setRestaurantName("Heaven");
////        restaurant2.setIsActive(true);
////        restaurant2.setRestaurantAvailability(true);
////
////        Menu menu1=new Menu(restaurant1,item,"Good",78D,20,true,true);
////        menu1.setId(1L);
////        Menu menu2=new Menu(restaurant2,item,"Good",78D,20,true,true);
////        menu2.setId(2L);
////
////        List<Menu> menuList=new ArrayList<>();
////        menuList.add(menu1);
////        menuList.add(menu2);
////
////        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
////        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant1));
////        when(restaurantRepository.findById(2L)).thenReturn(Optional.of(restaurant2));
////
////        List<Search> searchModelList=searchService.listSpecifiedItem(1L,0,5);
////
////        assertNotNull(searchModelList);
////        assertEquals(2,searchModelList.size());
////        assertEquals(1L,searchModelList.get(0).getItemId());
////        assertEquals(1L,searchModelList.get(0).getRestaurantId());
////        assertEquals(1L,searchModelList.get(1).getItemId());
////        assertEquals(2L,searchModelList.get(1).getRestaurantId());
////    }
//
//    /**
//     * This test case is to list a specified item with all restaurants which has the item.
//     * This test throws exception when an item is not thrown.
//     */
//    @Test
//    public void specifiedItemWhenNotFound()
//    {
//        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(DetailsNotFoundException.class,()->searchService.listSpecifiedItem(1L,0,5));
//    }
//
//    /**
//     * This test case is to list a specified item with all restaurants which has the item.
//     * This test throws exception when an item is  deleted from the table.
//     */
//    @Test
//    public void specifiedItemNotActive(){
//        Item item1=new Item();
//        item1.setId(1L);
//        item1.setIsActive(false);
//
//        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
//
//        assertThrows(DetailsNotFoundException.class,()->searchService.listSpecifiedItem(1L,0,5));
//    }
//
//    /**
//     * This test case is to list a specified item with all restaurants which has the item.
//     * This test throws exception when restaurant is not found.
//     */
//    @Test
//    public void specifiedItemRestaurantNotFound()
//    {
//        PageRequest pageRequest=PageRequest.of(0,5);
//
//        Item item=new Item();
//        item.setId(1L);
//        item.setItemName("Juice");
//        item.setIsActive(true);
//
//        Restaurant restaurant1 =new Restaurant();
//        restaurant1.setId(1L);
//        restaurant1.setRestaurantName("Paradise");
//        restaurant1.setIsActive(false);
//        restaurant1.setRestaurantAvailability(true);
//
//
//        Menu menu1=new Menu();
//        menu1.setId(1L);
//        menu1.setRestaurant(restaurant1);
//        menu1.setItem(item);
//        menu1.setIsActive(true);
//        menu1.setItemAvailability(true);
//
//
//
//
//        List<Menu> menuList=new ArrayList<>();
//        menuList.add(menu1);
//
//
//        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
//        when(menuRepository.findAllByitemId(1L,pageRequest)).thenReturn(new PageImpl<>(menuList));
//        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(DetailsNotFoundException.class,()->searchService.listSpecifiedItem(1L,0,5));
//    }
//
//    /**
//     * This test method is to test whether the method display all items under the specified restaurant.
//     */
//    @Test
//    public void listAllItemsRestaurantTest()
//    {
//        List<Search> searchModelList=new ArrayList<>();
//        PageRequest pageRequest=PageRequest.of(0,5);
//        Restaurant restaurant1 =new Restaurant();
//        restaurant1.setId(1L);
//        restaurant1.setRestaurantName("Paradise");
//        restaurant1.setIsActive(true);
//        restaurant1.setRestaurantAvailability(true);
//
//
//        Item item1=new Item();
//        item1.setId(1L);
//        item1.setItemName("Juice");
//        item1.setIsActive(true);
//
//
//        Item item2=new Item();
//        item2.setId(2L);
//        item2.setItemName("Cream");
//        item2.setIsActive(true);
//
//        Menu menu1=new Menu();
//        menu1.setId(1L);
//        menu1.setRestaurant(restaurant1);
//        menu1.setItem(item1);
//        menu1.setIsActive(true);
//        menu1.setItemAvailability(true);
//
//        Menu menu2=new Menu();
//        menu2.setId(2L);
//        menu2.setRestaurant(restaurant1);
//        menu2.setItem(item2);
//        menu2.setIsActive(true);
//        menu2.setItemAvailability(true);
//        List<Menu> menu=new ArrayList<>();
//
//        menu.add(0,menu1);
//        menu.add(1,menu2);
//
//        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant1));
//        when(menuRepository.findAllByrestaurantId(1L,pageRequest)).thenReturn(new PageImpl<>(menu));
//        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
//        when(itemRepository.findById(2L)).thenReturn(Optional.of(item2));
//
//       searchModelList=searchService.listAllItemsRestaurant(1L,0,5);
//
//        assertNotNull(searchModelList);
//        assertEquals(2,searchModelList.size());
//        assertEquals(1L,searchModelList.get(0).getItemId());
//        assertEquals(1L,searchModelList.get(0).getRestaurantId());
//        assertEquals(2L,searchModelList.get(1).getItemId());
//        assertEquals(1L,searchModelList.get(1).getRestaurantId());
//    }
//
//    /**
//     * This test method is to test whether the method display all items under the specified restaurant.
//     * This throws an exception when Restaurant is not found.
//     */
//
//    @Test
//    public void listAllItemsRestaurantNotFound()
//    {
//        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(DetailsNotFoundException.class,()->searchService.listAllItemsRestaurant(1L,0,5));
//    }
//
//    /**
//     * This test method is to test whether the method display all items under the specified restaurant.
//     * This throws an exception when Restaurant is not active.
//     */
//    @Test
//    public void listAllItemsRestaurantNotActive(){
//
//        Restaurant restaurant1 =new Restaurant();
//        restaurant1.setId(1L);
//        restaurant1.setRestaurantName("Paradise");
//        restaurant1.setIsActive(false);
//        restaurant1.setRestaurantAvailability(true);
//        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant1));
//        assertThrows(DetailsNotFoundException.class,()->searchService.listAllItemsRestaurant(1L,0,5));
//
//    }
//
//    /**
//     * This test method is to test whether the method display all items under the specified restaurant.
//     * This throws an exception when Item in that Restaurant is not found.
//     */
//
//    @Test
//    public void listAllItemsRestaurantItemNotFound(){
//        List<Search> searchModelList=new ArrayList<>();
//        PageRequest pageRequest=PageRequest.of(0,5);
//        Restaurant restaurant1 =new Restaurant();
//        restaurant1.setId(1L);
//        restaurant1.setRestaurantName("Paradise");
//        restaurant1.setIsActive(true);
//        restaurant1.setRestaurantAvailability(true);
//
//        Item item1=new Item();
//        item1.setItemName("Juice");
//        item1.setIsActive(true);
//
//
//        Menu menu1=new Menu();
//        menu1.setId(1L);
//        menu1.setRestaurant(restaurant1);
//        menu1.setIsActive(true);
//        menu1.setItem(item1);
//        menu1.setItemAvailability(true);
//
//
//        List<Menu> menu=new ArrayList<>();
//        menu.add(menu1);
//
//
//        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant1));
//        when(menuRepository.findAllByrestaurantId(1L,pageRequest)).thenReturn(new PageImpl<>(menu));
//        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(DetailsMissingException.class,()->searchService.listAllItemsRestaurant(1L,0,5));
//
//    }
//
//    /**
//     * This test method is to test whether the method display all items under the specified restaurant.
//     * This throws an exception when Item in that Restaurant is not active.
//     */
//    @Test
//    public void listAllItemsRestaurantItemNotActive(){
//
//        List<Search> searchModelList=new ArrayList<>();
//        PageRequest pageRequest=PageRequest.of(0,5);
//        Restaurant restaurant1 =new Restaurant();
//        restaurant1.setId(1L);
//        restaurant1.setRestaurantName("Paradise");
//        restaurant1.setIsActive(true);
//        restaurant1.setRestaurantAvailability(true);
//
//
//        Item item1=new Item();
//        item1.setId(1L);
//        item1.setItemName("Juice");
//        item1.setIsActive(false);
//
//
//
//        Menu menu1=new Menu();
//        menu1.setId(1L);
//        menu1.setRestaurant(restaurant1);
//        menu1.setItem(item1);
//        menu1.setIsActive(true);
//        menu1.setItemAvailability(true);
//
//        List<Menu> menu=new ArrayList<>();
//
//        menu.add(menu1);
//
//
//        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant1));
//        when(menuRepository.findAllByrestaurantId(1L,pageRequest)).thenReturn(new PageImpl<>(menu));
//        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
//
//        assertThrows(DetailsNotFoundException.class,()->searchService.listAllItemsRestaurant(1L,0,5));
//
//    }
//
//    /**
//     * This test method to test it gives exact output  to list all restaurants.
//     */
//
//    @Test
//    public void listAllRestaurantsTest()
//    {
//
//        PageRequest pageRequest=PageRequest.of(0,5);
//
//        Restaurant restaurant1 =new Restaurant();
//        restaurant1.setId(1L);
//        restaurant1.setRestaurantName("Paradise");
//        restaurant1.setIsActive(true);
//        restaurant1.setRestaurantAvailability(true);
//
//        Restaurant restaurant2 =new Restaurant();
//        restaurant2.setId(2L);
//        restaurant2.setRestaurantName("Heaven");
//        restaurant2.setIsActive(true);
//        restaurant2.setRestaurantAvailability(true);
//
//        List<Restaurant> restaurantEntity=new ArrayList<>();
//        restaurantEntity.add(restaurant1);
//        restaurantEntity.add(restaurant2);
//
//        List<com.thinkpalm.thinkfood.model.Restaurant> restaurantModel =new ArrayList<>();
//        when(restaurantRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(restaurantEntity));
//
//        restaurantModel=searchService.listAllRestaurants(0,5);
//
//        assertNotNull(restaurantModel);
//        assertEquals(2,restaurantModel.size());
//        assertEquals(1L,restaurantModel.get(0).getId());
//        assertEquals(2L,restaurantModel.get(1).getId());
//
//    }
//
//    /**
//     * This test method to test it gives exact output  to list all restaurants.
//     * this is to throw exception when null value present.
//     */
//
//    @Test
//    public void listAllRestaurantsNullValuePresent(){
//        PageRequest pageRequest=PageRequest.of(0,5);
//
//        when(restaurantRepository.findAll(pageRequest)).thenReturn(null);
//
//        assertThrows(DetailsMissingException.class,()->searchService.listAllRestaurants(0,5));
//    }
//
//    /**
//     * This test case is to test is we search restaurants in a given location.
//     */
//
//    @Test
//    public void listAllRestaurantsLocationTest()
//    {
//
//        Restaurant restaurant1 =new Restaurant();
//        restaurant1.setId(1L);
//        restaurant1.setRestaurantName("Paradise");
//        restaurant1.setIsActive(true);
//        restaurant1.setRestaurantAvailability(true);
//        restaurant1.setLatitude(2d);
//        restaurant1.setLongitude(3d);
//
//
//        Restaurant restaurant2 =new Restaurant();
//        restaurant2.setId(2L);
//        restaurant2.setRestaurantName("Heaven");
//        restaurant2.setIsActive(true);
//        restaurant2.setRestaurantAvailability(true);
//        restaurant2.setLatitude(4d);
//        restaurant2.setLongitude(5d);
//
//        List<Restaurant> restaurantEntity=new ArrayList<>();
//        restaurantEntity.add(restaurant1);
//        restaurantEntity.add(restaurant2);
//
//        List<com.thinkpalm.thinkfood.model.Restaurant> restaurantModel =new ArrayList<>();
//        when(restaurantRepository.findAll()).thenReturn(restaurantEntity);
//
//        restaurantModel=searchService.listAllRestaurantLocation(0d,0d);
//
//        assertNotNull(restaurantModel);
//        assertEquals(2,restaurantModel.size());
//        assertEquals(1L,restaurantModel.get(0).getId());
//        assertEquals(2L,restaurantModel.get(1).getId());
//
//    }
//
//    /**
//     * This test case is to test is we search restaurants in a given location.
//     * This to throw exception if we encounter null values
//     */
//    @Test
//    public void listAllRestaurantsLocationNullValuePresent() {
//        Restaurant restaurant1 =new Restaurant();
//        restaurant1.setId(1L);
//        restaurant1.setRestaurantName("Paradise");
//        restaurant1.setIsActive(true);
//        restaurant1.setRestaurantAvailability(true);
//
//
//
//        Restaurant restaurant2 =new Restaurant();
//        restaurant2.setId(2L);
//        restaurant2.setRestaurantName("Heaven");
//        restaurant2.setIsActive(true);
//        restaurant2.setRestaurantAvailability(true);
//
//
//        List<Restaurant> restaurantEntity=new ArrayList<>();
//        restaurantEntity.add(restaurant1);
//        restaurantEntity.add(restaurant2);
//
//        List<com.thinkpalm.thinkfood.model.Restaurant> restaurantModel =new ArrayList<>();
//        when(restaurantRepository.findAll()).thenReturn(restaurantEntity);
//
//
//
//        assertThrows(DetailsMissingException.class,()->searchService.listAllRestaurantLocation(0d,0d));
//    }
//
//    @Test
//    public void listAllItemLocationTest()
//    {
//
//        Restaurant restaurant1 =new Restaurant();
//        restaurant1.setId(1L);
//        restaurant1.setRestaurantName("Paradise");
//        restaurant1.setIsActive(true);
//        restaurant1.setRestaurantAvailability(true);
//        restaurant1.setLatitude(2d);
//        restaurant1.setLongitude(3d);
//
//        Item item1=new Item();
//        item1.setId(1L);
//        item1.setItemName("Juice");
//        item1.setIsActive(true);
//
//        Menu menu1=new Menu();
//        menu1.setId(1L);
//        menu1.setRestaurant(restaurant1);
//        menu1.setItem(item1);
//        menu1.setIsActive(true);
//        menu1.setItemAvailability(true);
//
//        List<Menu> menuEntity=new ArrayList<>();
//        menuEntity.add(menu1);
//
//        List<Restaurant> restaurantEntity=new ArrayList<>();
//        restaurantEntity.add(restaurant1);
//
//        when(restaurantRepository.findAll()).thenReturn(restaurantEntity);
//        when(menuRepository.findByRestaurantId(1L)).thenReturn(menuEntity);
//        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
//
//        List<com.thinkpalm.thinkfood.model.Item> itemModel=searchService.listAllItemsLocation(0d,0d);
//
//        assertNotNull(itemModel);
//        assertEquals(1L,itemModel.get(0).getId());
//    }
//}
//
//
//
//

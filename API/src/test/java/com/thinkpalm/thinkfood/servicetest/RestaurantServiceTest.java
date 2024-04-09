package com.thinkpalm.thinkfood.servicetest;

import com.thinkpalm.thinkfood.entity.*;
import com.thinkpalm.thinkfood.exception.EmptyInputException;;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.repository.MenuRepository;
import com.thinkpalm.thinkfood.repository.OrderRepository;
import com.thinkpalm.thinkfood.repository.RestaurantRepository;
import com.thinkpalm.thinkfood.repository.UserRepository;

import com.thinkpalm.thinkfood.service.OrderServiceImplementation;
import com.thinkpalm.thinkfood.service.RestaurantServiceImplementation;;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @InjectMocks
    private RestaurantServiceImplementation restaurantService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;


    @Mock
    private RestaurantRepository restaurantRepository;


    @Test
    public void testGetRestaurantByIdRestaurantFoundAndIsActive() throws NotFoundException {
        com.thinkpalm.thinkfood.entity.Restaurant restaurantEntity = new com.thinkpalm.thinkfood.entity.Restaurant();
        restaurantEntity.setId(1L);
        restaurantEntity.setIsActive(true);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));

        com.thinkpalm.thinkfood.model.Restaurant restaurant = restaurantService.getRestaurantById(1L);

        assertNotNull(restaurant);
        assertEquals(1L, restaurant.getId());
    }

    @Test
    public void testGetRestaurantByIdRestaurantNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> restaurantService.getRestaurantById(1L));
    }

    @Test
    public void testGetRestaurantByIdRestaurantNotActive() {
        com.thinkpalm.thinkfood.entity.Restaurant restaurantEntity = new com.thinkpalm.thinkfood.entity.Restaurant();
        restaurantEntity.setId(1L);
        restaurantEntity.setIsActive(false);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));

        assertThrows(NotFoundException.class, () -> restaurantService.getRestaurantById(1L));
    }

    @Test
    void testUpdatePreparationStatusWhenStatusIsPreparation() throws NotFoundException {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setPreparationStatus(String.valueOf(PreparationStatus.PREPARATION));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        String result = restaurantService.updatePreparationStatus(orderId);

        assertEquals("Status Updated", result);
        assertEquals(String.valueOf(PreparationStatus.OUT_FOR_DELIVERY), order.getPreparationStatus());
    }

    @Test
    void testUpdatePreparationStatusWhenStatusIsNotPreparation() throws NotFoundException {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setPreparationStatus(String.valueOf(PreparationStatus.OUT_FOR_DELIVERY));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        String result = restaurantService.updatePreparationStatus(orderId);

        assertEquals("Status Updated", result);
        assertEquals(String.valueOf(PreparationStatus.OUT_FOR_DELIVERY), order.getPreparationStatus());
    }

    @Test
    void testUpdatePreparationStatusWhenOrderNotFound() {

        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> restaurantService.updatePreparationStatus(orderId));
    }


    @Test
    void testCreateRestaurant() throws EmptyInputException {
        com.thinkpalm.thinkfood.model.Restaurant restaurant = new com.thinkpalm.thinkfood.model.Restaurant();
        restaurant.setRestaurantName("Example Restaurant");
        restaurant.setRestaurantDescription("Description");
        restaurant.setLatitude(123.456);
        restaurant.setLongitude(789.012);
        restaurant.setPhoneNumber("123-456-7890");
        restaurant.setEmail("example@example.com");
        restaurant.setRestaurantAvailability(true);

        com.thinkpalm.thinkfood.entity.Restaurant restaurantEntity = new com.thinkpalm.thinkfood.entity.Restaurant();
        restaurantEntity.setRestaurantName(restaurant.getRestaurantName());
        restaurantEntity.setRestaurantDescription(restaurant.getRestaurantDescription());
        restaurantEntity.setLatitude(restaurant.getLatitude());
        restaurantEntity.setLongitude(restaurant.getLongitude());
        restaurantEntity.setPhoneNumber(restaurant.getPhoneNumber());
        restaurantEntity.setEmail(restaurant.getEmail());
        restaurantEntity.setRestaurantAvailability(restaurant.getRestaurantAvailability());

        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurantEntity);

        com.thinkpalm.thinkfood.model.Restaurant result = restaurantService.createRestaurant(restaurant);

        assertNotNull(result);

        assertEquals("Example Restaurant", result.getRestaurantName());
        assertEquals("Description", result.getRestaurantDescription());
        assertEquals(123.456, result.getLatitude());
        assertEquals(789.012, result.getLongitude());
        assertEquals("123-456-7890", result.getPhoneNumber());
        assertEquals("example@example.com", result.getEmail());
        assertTrue(result.getRestaurantAvailability());
    }

    @Test
    void testCreateRestaurantWithMissingFields() {
        com.thinkpalm.thinkfood.model.Restaurant restaurant = new com.thinkpalm.thinkfood.model.Restaurant();

        assertThrows(EmptyInputException.class, () -> restaurantService.createRestaurant(restaurant));
    }


    @Test
    void testUpdateRestaurant() throws NotFoundException {
        Long restaurantId = 3L;
        com.thinkpalm.thinkfood.model.Restaurant updatedRestaurant = new com.thinkpalm.thinkfood.model.Restaurant();
        updatedRestaurant.setRestaurantName("Updated Restaurant");
        updatedRestaurant.setRestaurantDescription("Updated Description");
        updatedRestaurant.setLatitude(123.456);
        updatedRestaurant.setLongitude(789.012);
        updatedRestaurant.setPhoneNumber("987-654-3210");
        updatedRestaurant.setEmail("updated@example.com");
        updatedRestaurant.setRestaurantAvailability(true);

        com.thinkpalm.thinkfood.entity.Restaurant restaurantEntity = new com.thinkpalm.thinkfood.entity.Restaurant();
        restaurantEntity.setId(restaurantId);
        restaurantEntity.setRestaurantName("Updated Restaurant");
        restaurantEntity.setRestaurantDescription("Updated Description");
        restaurantEntity.setLatitude(123.456);
        restaurantEntity.setLongitude(789.01);
        restaurantEntity.setPhoneNumber("987-654-3210");
        restaurantEntity.setEmail("updated@example.com");
        restaurantEntity.setRestaurantAvailability(true);
        restaurantEntity.setIsActive(true);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));
        when(restaurantRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));


        com.thinkpalm.thinkfood.model.Restaurant result = restaurantService.updateRestaurant(restaurantId, updatedRestaurant);

        assertNotNull(result);


        assertEquals(updatedRestaurant.getRestaurantName(), result.getRestaurantName());
        assertEquals(updatedRestaurant.getRestaurantDescription(), result.getRestaurantDescription());
        assertEquals(updatedRestaurant.getLatitude(), result.getLatitude());
        assertEquals(updatedRestaurant.getLongitude(), result.getLongitude());
        assertEquals(updatedRestaurant.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(updatedRestaurant.getEmail(), result.getEmail());
        assertEquals(updatedRestaurant.getRestaurantAvailability(), result.getRestaurantAvailability());

    }


    @Test
    void testUpdateRestaurantNotFound() {
        Long restaurantId = 1L;

        com.thinkpalm.thinkfood.model.Restaurant updatedRestaurant = new com.thinkpalm.thinkfood.model.Restaurant();
        updatedRestaurant.setRestaurantName("Updated Restaurant");
        updatedRestaurant.setRestaurantDescription("Updated Description");
        updatedRestaurant.setLatitude(123.456);
        updatedRestaurant.setLongitude(789.012);
        updatedRestaurant.setPhoneNumber("987-654-3210");
        updatedRestaurant.setEmail("updated@example.com");
        updatedRestaurant.setRestaurantAvailability(true);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> restaurantService.updateRestaurant(restaurantId, updatedRestaurant));
    }

    @Test
    void testUpdateRestaurantWhenNotActive() {

        Long restaurantId = 1L;
        com.thinkpalm.thinkfood.model.Restaurant updatedRestaurant = new com.thinkpalm.thinkfood.model.Restaurant();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));

        assertThrows(NotFoundException.class, () -> restaurantService.updateRestaurant(restaurantId, updatedRestaurant));
    }

    @Test
    void testDeleteRestaurantById() throws NotFoundException {
        Long restaurantId = 1L;

        com.thinkpalm.thinkfood.entity.Restaurant restaurantEntity = new com.thinkpalm.thinkfood.entity.Restaurant();
        restaurantEntity.setId(restaurantId);

        Optional<com.thinkpalm.thinkfood.entity.Restaurant> restaurantOptional = Optional.of(restaurantEntity);
        when(restaurantRepository.findById(restaurantId)).thenReturn(restaurantOptional);

        List<Menu> relatedMenus = new ArrayList<>();
        Menu menu = new Menu();
        menu.setId(1L);
        relatedMenus.add(menu);

        when(menuRepository.findByRestaurantId(restaurantId)).thenReturn(relatedMenus);

        User associatedUser = new User();
        associatedUser.setId(1L);
        restaurantEntity.setUser(associatedUser);

        com.thinkpalm.thinkfood.model.Restaurant deletedRestaurant = restaurantService.deleteRestaurantById(restaurantId);

        assertNotNull(deletedRestaurant);
        assertEquals(restaurantId, deletedRestaurant.getId());

        verify(menuRepository, times(1)).delete(menu);
        verify(userRepository, times(1)).delete(associatedUser);
        verify(restaurantRepository, times(1)).deleteById(restaurantId);
    }
    @Test
    void testDeleteRestaurantByIdNotFound() {
        Long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> restaurantService.deleteRestaurantById(restaurantId));

        verify(menuRepository, never()).delete(any(Menu.class));
        verify(userRepository, never()).delete(any(User.class));
        verify(restaurantRepository, never()).deleteById(restaurantId);
    }

    @Test
    void testSoftDeleteRestaurantById() throws NotFoundException {
        Long restaurantId = 1L;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setUser(new User());
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        List<Menu> menus = new ArrayList<>();
        Menu menu1 = new Menu();
        menu1.setId(1L);
        menus.add(menu1);
        when(menuRepository.findByRestaurantId(restaurantId)).thenReturn(menus);

        User user = new User();
        user.setId(1L);
        when(userRepository.save(any())).thenReturn(user);

        String result = restaurantService.softDeleteRestaurantById(restaurantId);

        assertEquals("Restaurant with ID " + restaurantId + " deleted successfully!", result);
        assertFalse(restaurant.getIsActive());
        assertFalse(user.getIsActive());
        for (Menu menu : menus) {
            assertFalse(menu.getIsActive());
        }
    }

    @Test
    void testSoftDeleteRestaurantByIdNotFound() {
        Long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> restaurantService.softDeleteRestaurantById(restaurantId));
    }






}



package com.thinkpalm.thinkfood.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.thinkpalm.thinkfood.entity.Menu;
import com.thinkpalm.thinkfood.exception.*;
import com.thinkpalm.thinkfood.model.UpdateMenu;
import com.thinkpalm.thinkfood.service.MenuServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thinkpalm.thinkfood.entity.Item;
import com.thinkpalm.thinkfood.entity.Restaurant;
import com.thinkpalm.thinkfood.repository.ItemRepository;
import com.thinkpalm.thinkfood.repository.MenuRepository;
import com.thinkpalm.thinkfood.repository.RestaurantRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private MenuServiceImplementation menuService;

    @Test
    public void testGetMenuByIdMenuFoundAndIsActive() throws NotFoundException {
        com.thinkpalm.thinkfood.entity.Menu menuEntity = new com.thinkpalm.thinkfood.entity.Menu();
        menuEntity.setId(1L);
        menuEntity.setIsActive(true);

        Restaurant restaurantEntity = new Restaurant();
        restaurantEntity.setId(2L);

        Item itemEntity = new Item();
        itemEntity.setId(3L);

        menuEntity.setRestaurant(restaurantEntity);
        menuEntity.setItem(itemEntity);

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menuEntity));

        com.thinkpalm.thinkfood.model.Menu menu = menuService.getMenuById(1L);

        assertNotNull(menu);
        assertEquals(1L, menu.getId());
        assertEquals(2L, menu.getRestaurantId());
        assertEquals(3L, menu.getItemId());
    }

    @Test
    public void testGetMenuByIdMenuNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> menuService.getMenuById(1L));
    }

    @Test
    public void testGetMenuByIdMenuNotActive() {
        com.thinkpalm.thinkfood.entity.Menu menuEntity = new com.thinkpalm.thinkfood.entity.Menu();
        menuEntity.setId(1L);
        menuEntity.setIsActive(false);

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menuEntity));

        assertThrows(NotFoundException.class, () -> menuService.getMenuById(1L));
    }


    @Test
    void createMenuWithValidData() throws EmptyInputException, NotFoundException {
        com.thinkpalm.thinkfood.model.Menu menu = new com.thinkpalm.thinkfood.model.Menu();
        menu.setRestaurantId(12L);
        menu.setItemId(1L);

        menu.setItemDescription("xdbc");
        menu.setItemPrice(80.0);
        menu.setPreparationTime(15);
        menu.setItemAvailability(true);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(12L);
        restaurant.setIsActive(true);
        Item item = new Item();
        item.setId(1L);
        item.setIsActive(true);

        when(restaurantRepository.findById(12L)).thenReturn(Optional.of(restaurant));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(menuRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        com.thinkpalm.thinkfood.model.Menu createdMenu = menuService.createMenu(menu);

        assertNotNull(createdMenu);
        assertEquals(12L, createdMenu.getRestaurantId());
        assertEquals(1L, createdMenu.getItemId());
        assertEquals("xdbc", createdMenu.getItemDescription());
        assertEquals(80.0, createdMenu.getItemPrice());
        assertEquals(15, createdMenu.getPreparationTime());
        assertTrue(createdMenu.getItemAvailability());
    }
    @Test
    public void testCreateMenuEmptyInput() {
        com.thinkpalm.thinkfood.model.Menu menuInput = new com.thinkpalm.thinkfood.model.Menu();

        assertThrows(EmptyInputException.class, () -> menuService.createMenu(menuInput));
    }


    @Test
    public void testUpdateMenuSuccess() throws NotFoundException {
        com.thinkpalm.thinkfood.entity.Menu menuEntity = new com.thinkpalm.thinkfood.entity.Menu();
        menuEntity.setId(1L);
        menuEntity.setIsActive(true);

        UpdateMenu updatedMenu = new UpdateMenu();
        updatedMenu.setItemDescription("Updated Item");
        updatedMenu.setItemPrice(15.0);
        updatedMenu.setPreparationTime(45);
        updatedMenu.setItemAvailability(true);

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menuEntity));
        when(menuRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateMenu updatedMenuResponse = menuService.updateMenu(1L, updatedMenu);

        assertNotNull(updatedMenuResponse);
        assertEquals("Updated Item", updatedMenuResponse.getItemDescription());
        assertEquals(15.0, updatedMenuResponse.getItemPrice());
        assertEquals(45, updatedMenuResponse.getPreparationTime());
        assertTrue(updatedMenuResponse.getItemAvailability());
    }

    @Test
    void updateMenuNotFoundExceptionThrown() {
        long menuId = 5L;
        UpdateMenu updatedMenu = new UpdateMenu();

        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> menuService.updateMenu(menuId, updatedMenu));
    }

    @Test
    void updateMenuNotActiveThrowsNotFoundException() {
        long menuId = 22L;
        UpdateMenu updatedMenu = new UpdateMenu();

        com.thinkpalm.thinkfood.entity.Menu existingMenu = new com.thinkpalm.thinkfood.entity.Menu();
        existingMenu.setId(menuId);
        existingMenu.setItemDescription("Tasty");
        existingMenu.setItemPrice(80.5);
        existingMenu.setPreparationTime(20);
        existingMenu.setItemAvailability(false);

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(existingMenu));

        assertThrows(NotFoundException.class, () -> menuService.updateMenu(menuId, updatedMenu));
    }

    @Test
    void deleteMenuById_ValidId_Success() throws NotFoundException {
        long menuId = 22L;

        Menu existingMenu = new Menu();
        existingMenu.setId(menuId);


        when(menuRepository.findById(menuId)).thenReturn(Optional.of(existingMenu));
        doNothing().when(menuRepository).deleteById(menuId);

        com.thinkpalm.thinkfood.model.Menu deletedMenu = menuService.deleteMenuById(menuId);

        // Assert
        assertNotNull(deletedMenu);
        assertEquals(existingMenu.getId(), deletedMenu.getId());
    }

    @Test
    void deleteMenuByIdNotFoundExceptionThrown() {
        long menuId = 22L;

        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> menuService.deleteMenuById(menuId));
    }

    @Test
    void testSoftDeleteMenuById() throws NotFoundException {
        Long menuId = 1L;
        com.thinkpalm.thinkfood.entity.Menu menuEntity = new com.thinkpalm.thinkfood.entity.Menu();
        menuEntity.setId(menuId);
        menuEntity.setIsActive(true);

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));

        String result = menuService.softDeleteMenuById(menuId);

        assertFalse(menuEntity.getIsActive());
        verify(menuRepository, times(1)).save(menuEntity);
        assertEquals("Menu With Id " + menuId + " deleted successfully!", result);
    }

    @Test
    void testSoftDeleteMenuByIdMenuNotFound() {
        Long menuId = 1L;

        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> menuService.softDeleteMenuById(menuId));
    }

    @Test
    public void testSoftDeleteMenuByMenuNotActive() {
        Long menuId = 1L;
        com.thinkpalm.thinkfood.entity.Menu menu = new com.thinkpalm.thinkfood.entity.Menu();
        menu.setId(menuId);
        menu.setIsActive(false);


        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            menuService.softDeleteMenuById(menuId);
        });
        assertEquals("Menu with ID " + menuId + " not found", exception.getMessage());
    }



}

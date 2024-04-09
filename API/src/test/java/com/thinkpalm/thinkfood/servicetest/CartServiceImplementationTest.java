package com.thinkpalm.thinkfood.servicetest;

import com.thinkpalm.thinkfood.entity.*;
import com.thinkpalm.thinkfood.exception.NotAvailableException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.exception.NotSameRestaurantException;
import com.thinkpalm.thinkfood.repository.CartItemRepository;
import com.thinkpalm.thinkfood.repository.CartRepository;
import com.thinkpalm.thinkfood.repository.CustomerRepository;
import com.thinkpalm.thinkfood.repository.MenuRepository;
import com.thinkpalm.thinkfood.service.CartService;
import com.thinkpalm.thinkfood.service.CartServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplementationTest {
    @Mock
    private MenuRepository menuRepository;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImplementation cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);}

    @Test
    void testCreateCart() throws NotFoundException {
        com.thinkpalm.thinkfood.model.Cart newCart = new com.thinkpalm.thinkfood.model.Cart();
        newCart.setCustomer(34L);
        newCart.setCart(90L);
        newCart.setTotalAmount(44D);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        com.thinkpalm.thinkfood.entity.Menu menu = new Menu();
        menu.setId(1L);
        menu.setRestaurant(restaurant);
        menu.setItemAvailability(true);
        menu.setItemPrice(22D);

        com.thinkpalm.thinkfood.model.CartItem cartItem = new com.thinkpalm.thinkfood.model.CartItem();
        cartItem.setId(1L);
        cartItem.setItemId(2L);
        cartItem.setQuantity(2);
        cartItem.setItemPrice(22D);
        cartItem.setSubtotal(44D);



        List<com.thinkpalm.thinkfood.model.CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);

        newCart.setCartItem(cartItems);

        Customer customer = new Customer();
        customer.setId(34L);

        when(customerRepository.findById(34L)).thenReturn(Optional.of(customer));
        when(menuRepository.findById(any())).thenReturn(Optional.of(menu));
        when(cartRepository.save(any())).thenReturn(new com.thinkpalm.thinkfood.entity.Cart());
        com.thinkpalm.thinkfood.model.Cart createdCart=new com.thinkpalm.thinkfood.model.Cart();

        createdCart.setCartItem(newCart.getCartItem());
        createdCart.setCart(newCart.getCart());
        createdCart.setCustomer(newCart.getCustomer());
        createdCart.setTotalAmount(newCart.getTotalAmount());

        com.thinkpalm.thinkfood.model.Cart result = cartService.createCart(createdCart);

        assertNotNull(result);
        assertEquals(44D, result.getTotalAmount());
//        assertEquals(90L, result.getCart());
        assertEquals(34L, result.getCustomer());
        assertEquals(1, result.getCartItem().size());
        com.thinkpalm.thinkfood.model.CartItem cartItem1 = result.getCartItem().get(0);
        assertEquals(2, cartItem1.getQuantity());
        assertEquals(22D, cartItem1.getItemPrice());
        assertEquals(44D, cartItem1.getSubtotal());

    }
    @Test
    void testUpdateCart() throws NotFoundException{
        com.thinkpalm.thinkfood.entity.Cart existingCart=new com.thinkpalm.thinkfood.entity.Cart();
        existingCart.setId(90L);
        existingCart.setTotalAmount(44D);
        existingCart.setIsActive(true);
        Restaurant restaurant=new Restaurant();
        restaurant.setId(1L);

        Menu menu=new Menu();
        menu.setId(1L);
        menu.setRestaurant(restaurant);
        menu.setItemAvailability(true);
        menu.setItemPrice(20D);

        com.thinkpalm.thinkfood.entity.CartItem existingCartItem = new com.thinkpalm.thinkfood.entity.CartItem();
        existingCartItem.setId(10L);
        existingCartItem.setMenu(menu);
        existingCartItem.setQuantity(2);
        existingCartItem.setItemPrice(20D);
        existingCartItem.setSubtotal(40D);

        List<com.thinkpalm.thinkfood.entity.CartItem> cartItems=new ArrayList<>();
        cartItems.add(existingCartItem);

        com.thinkpalm.thinkfood.entity.CartItem newCartItem=new com.thinkpalm.thinkfood.entity.CartItem();
        newCartItem.setId(11L);
        newCartItem.setMenu(menu);
        newCartItem.setQuantity(3);
        newCartItem.setItemPrice(20D);
        newCartItem.setSubtotal(60D);
        cartItems.add(newCartItem);
        existingCart.setCartItems(cartItems);
        existingCart.setTotalAmount(100D);

        Customer customer=new Customer();
        customer.setId(34L);
        existingCart.setCustomer(customer);
        when(customerRepository.findById(34L)).thenReturn(Optional.of(customer));
        when(menuRepository.findById(any())).thenReturn(Optional.of(menu));
//        when(cartRepository.save(any())).thenReturn(Optional.of(existingCart));
        when(cartItemRepository.saveAll(cartItems)).thenReturn(cartItems);
        when(cartRepository.findById(90L)).thenReturn(Optional.of(existingCart));
        List<com.thinkpalm.thinkfood.model.CartItem> cartItemList=new ArrayList<>();
        com.thinkpalm.thinkfood.model.CartItem existingCartItemModel=new com.thinkpalm.thinkfood.model.CartItem();
        existingCartItemModel.setId(existingCartItem.getId());
        existingCartItemModel.setItemId(existingCartItem.getMenu().getId());
        existingCartItemModel.setQuantity(existingCartItemModel.getQuantity());
        existingCartItemModel.setItemPrice(existingCartItemModel.getItemPrice());
        existingCartItemModel.setSubtotal(existingCartItemModel.getSubtotal());
        cartItemList.add(existingCartItemModel);

        com.thinkpalm.thinkfood.model.CartItem newCartItemModel=new com.thinkpalm.thinkfood.model.CartItem();
        newCartItemModel.setId(newCartItem.getId());
        newCartItemModel.setItemId(newCartItem.getMenu().getId());
        newCartItemModel.setQuantity(newCartItemModel.getQuantity());
        newCartItemModel.setItemPrice(newCartItemModel.getItemPrice());
        newCartItemModel.setSubtotal(newCartItemModel.getSubtotal());
        cartItemList.add(newCartItemModel);

        com.thinkpalm.thinkfood.model.Cart existingCartModel=new com.thinkpalm.thinkfood.model.Cart();
        existingCartModel.setCartItem(cartItemList);
        existingCartModel.setCart(existingCart.getId());
        existingCartModel.setCustomer(existingCart.getCustomer().getId());
        existingCart.setTotalAmount(existingCart.getTotalAmount());

        com.thinkpalm.thinkfood.model.Cart result=cartService.updateCart(existingCartModel);

        assertNotNull(result);
//        assertEquals(100D,result.getTotalAmount());
//        assertEquals(90L,result.getCart());
        assertEquals(34L,result.getCustomer());
        assertEquals(2,result.getCartItem().size());
    }
    @Test
    void testFindCartById() throws NotFoundException{
        Long cartId =123L;
        Cart cart=new Cart();
        cart.setId(cartId);

        Customer customer=new Customer();
        customer.setId(34L);

        cart.setCustomer(customer);
        cart.setTotalAmount(60D);
        cart.setIsActive(Boolean.TRUE);

        CartItem cartItem=new CartItem();
        cartItem.setId(12L);
        cartItem.setItemPrice(20D);
        cartItem.setQuantity(3);
        cartItem.setSubtotal(60D);
        cartItem.setIsActive(true);

        Menu menu = new Menu();
        menu.setItemPrice(20D);
        menu.setId(1L);
        menu.setItemAvailability(true);
        cartItem.setMenu(menu);

        List<CartItem> cartItems=new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        com.thinkpalm.thinkfood.model.Cart result=cartService.findCartById(cartId);

        assertNotNull(result);
        assertEquals(123L,result.getCart());
        assertEquals(34L,result.getCustomer());
        assertEquals(12L,result.getCartItem().get(0).getId());
        assertEquals(20D,result.getCartItem().get(0).getItemPrice());
        assertEquals(3,result.getCartItem().get(0).getQuantity());
        assertEquals(60D,result.getCartItem().get(0).getSubtotal());
        assertEquals(60D,result.getTotalAmount());
        assertEquals(1L,result.getCartItem().get(0).getItemId());
    }

    @Test
    void deleteCart_ValidId_Success() throws NotFoundException{
        long cartId = 200L;
        Cart existingCart=new Cart();
        existingCart.setIsActive(Boolean.TRUE);
        existingCart.setId(cartId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(existingCart));

        com.thinkpalm.thinkfood.model.Cart deleteCart=cartService.deleteCart(cartId);

        assertNotNull(deleteCart);
        assertEquals(existingCart.getId(),deleteCart.getCart());

    }

    @Test
    void deleteCartNotFoundExceptionThrown(){
        long nonExistentCartId=123L;
        when(cartRepository.findById(nonExistentCartId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,()->cartService.deleteCart(nonExistentCartId));
    }

    @Test
    void testDeleteCartItem_validId_CartItemDeletedSuccessfully() throws NotFoundException{
        long cartId=30L;
        long cartItemId=13L;
        Cart existingCart=new Cart();
        existingCart.setIsActive(Boolean.TRUE);
        existingCart.setId(cartId);
        existingCart.setTotalAmount(500D);

        CartItem cartItem=new CartItem();
        cartItem.setIsActive(Boolean.TRUE);
        cartItem.setId(cartItemId);
        cartItem.setCart(existingCart);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(existingCart));
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));
        String result=cartService.deleteItemCart(cartId,cartItemId);

        assertNotNull(result);
        assertEquals("CartItem deleted successfully",result);
    }
    @Test
    void testDeleteItemCart_ItemNotBelongsToCart() {
        long cartId = 1L;
        long cartItemId = 2L;

        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        cartItem.setSubtotal(25.0);
        Cart differentCart=new Cart();
        differentCart.setId(cartId);

        com.thinkpalm.thinkfood.entity.Cart cart = new com.thinkpalm.thinkfood.entity.Cart();
        cart.setId(3L);
        cart.setIsActive(true);
        cart.setTotalAmount(120D);
        cartItem.setCart(cart);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(differentCart));
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));

        String result = cartService.deleteItemCart(cartId, cartItemId);

        assertNotNull(result);
        assertEquals("CartItem is not found or it does not belong to the specified cart " + cartId, result);

        verify(cartRepository, times(1)).findById(cartId);
        verify(cartItemRepository, times(1)).findById(cartItemId);
        verify(cartItemRepository, never()).delete(cartItem);
        verify(cartRepository, never()).save(cart);
    }

    @Test
    void softDeleteCart_validId_cartDeletedSuccessfully() throws NotFoundException{
        long cartId=30L;

        Cart cart=new Cart();
        cart.setId(cartId);
        cart.setIsActive(true);

        CartItem cartItem=new CartItem();
        cartItem.setIsActive(true);

        cart.setCartItems(List.of(cartItem));

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));


        String result=cartService.softDeleteCart(cartId);

        assertNotNull(result);
        assertEquals("Cart with Id "+cartId+" deleted successfully!",result);
        assertFalse(cart.getIsActive());

        for(CartItem item : cart.getCartItems()){
            assertFalse(item.getIsActive());
        }

        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(cart);

    }

    @Test
    void sofDeleteCart_invalidId_shouldThrowNotFoundException(){
        long invalidCartId=23L;

        when(cartRepository.findById(invalidCartId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, ()->{
            cartService.softDeleteCart(invalidCartId);
        });
    }

    @Test
    void softDeleteCart_inactiveCart_shouldThrowNotFoundException() {
        long inactiveCartId=56L;
        Cart inactiveCart=new Cart();
        inactiveCart.setId(inactiveCartId);
        inactiveCart.setIsActive(false);

        when(cartRepository.findById(inactiveCartId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, ()-> {
            cartService.softDeleteCart(inactiveCartId);
        });
    }

    @Test
    void softDeleteCartItem_validId_cartItemDeletedSuccessfully() throws NotFoundException{
        long cartId=23L;
        long cartItemId=12L;

        Cart cart=new Cart();
        cart.setId(cartId);
        cart.setIsActive(true);
        cart.setTotalAmount(140D);

        CartItem cartItem=new CartItem();
        cartItem.setCart(cart);
        cartItem.setId(cartItemId);
        cartItem.setIsActive(true);
        cartItem.setItemPrice(20D);
        cartItem.setQuantity(3);
        cartItem.setSubtotal(60D);

        cart.setCartItems(List.of(cartItem));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));

        String result=cartService.softDeleteCartItem(cartId,cartItemId);

        assertNotNull(result);
        assertEquals("CartItem with Id "+cartItemId+" present in Cart "+cartId+" is deleted successfully!",result);
        assertFalse(cartItem.getIsActive());
        assertEquals(80D,cart.getTotalAmount());

        verify(cartRepository, times(1)).findById(cartId);
        verify(cartItemRepository, times(1)).findById(cartItemId);
        verify(cartItemRepository, times(1)).save(cartItem);

    }

    @Test
    void softDeleteCartItem_invalidId_shouldThrowNotFoundException(){
        long cartId=34L;
        long invalidCartItemId=14L;

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,()->{
            cartService.softDeleteCartItem(cartId,invalidCartItemId);
        });

    }

    @Test
    void softDeleteCartItem_inactiveCartItem_shouldThrowNotFoundException(){
        long cartId=6L;
        long inactiveCartItemId=30L;

        Cart cart=new Cart();
        cart.setId(cartId);

        CartItem cartItem=new CartItem();
        cartItem.setId(inactiveCartItemId);
        cartItem.setCart(cart);
        cartItem.setIsActive(false);

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, ()->{
            cartService.softDeleteCartItem(cartId,inactiveCartItemId);
        });

    }


}
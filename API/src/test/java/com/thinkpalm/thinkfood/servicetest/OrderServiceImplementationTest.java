package com.thinkpalm.thinkfood.servicetest;

import com.thinkpalm.thinkfood.entity.*;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.repository.*;
import com.thinkpalm.thinkfood.service.CouponServiceImplementation;
import com.thinkpalm.thinkfood.service.OrderServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplementationTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrderServiceImplementation orderService;

    @InjectMocks
    private CouponServiceImplementation couponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() throws NotFoundException {
        com.thinkpalm.thinkfood.model.Order newOrder = new com.thinkpalm.thinkfood.model.Order();
        newOrder.setCouponCode("FLAT50");
        newOrder.setCartId(12L);

        Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setDiscountPercentage(3.2);
        coupon.setCouponCode("FLAT50");
        coupon.setIsActive(Boolean.TRUE);

        Customer customer = new Customer();
        customer.setId(34L);
        customer.setAddress("123 Main Street");
        customer.setCustomerName("krishna");

        Cart cart = new Cart();
        cart.setId(12L);
        cart.setCustomer(customer);
        cart.setTotalAmount(240D);


        CartItem cartItem = new CartItem();
        cartItem.setSubtotal(50.0);
        cartItem.setItemPrice(10.0);
        cartItem.setQuantity(5);

        Menu menu = new Menu();
        Item item = new Item();
        item.setItemName("strawberry ice cream");
        menu.setItem(item);
        menu.setRestaurant(new Restaurant());
        cartItem.setMenu(menu);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);


        when(couponRepository.findByCouponCode("FLAT50")).thenReturn(Optional.of(coupon));
        when(cartRepository.findById(12L)).thenReturn(Optional.of(cart));
        when(customerRepository.findById(34L)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any())).thenReturn(new Order());

        com.thinkpalm.thinkfood.model.Order result = orderService.createOrder(newOrder);


        assertNotNull(result);
        assertEquals(274.1376, result.getTotalCost());
        assertEquals("123 Main Street", result.getAddress());
        assertEquals("krishna", result.getCustomerName());
        assertEquals(12L, result.getCartId());
        assertEquals(1, result.getOrderedItems().size());

        com.thinkpalm.thinkfood.model.OrderedItem orderedItem = result.getOrderedItems().get(0);
        assertEquals("strawberry ice cream", orderedItem.getName());
        assertEquals(5, orderedItem.getQuantity());
        assertEquals(50.0, orderedItem.getSubTotal());
        assertEquals(10.0, orderedItem.getPrice());
        assertNull(orderedItem.getRestaurant());
    }

    @Test
    void testUpdateCoupon() throws NotFoundException {

        Long orderId = 152L;
        String newCouponCode = "FLAT22";



        Order order = new Order();
        order.setId(orderId);
        order.setIsActive(Boolean.TRUE);

        Cart cart = new Cart();
        cart.setTotalAmount(120.0);

        order.setCart(cart);

        Coupon newCoupon = new Coupon();
        newCoupon.setCouponCode(newCouponCode);
        newCoupon.setDiscountPercentage(3.2);
        newCoupon.setId(8L);
        newCoupon.setIsActive(Boolean.TRUE);


        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(couponRepository.findByCouponCode(newCouponCode)).thenReturn(Optional.of(newCoupon));

        orderService.updateCoupon(orderId, newCouponCode);

        verify(orderRepository, times(1)).findById(orderId);
        verify(couponRepository, times(1)).findByCouponCode(newCouponCode);


    }
    @Test
    void testFindOrderById() throws NotFoundException {
        Long orderId = 152L;
        Order order = new Order();
        order.setId(orderId);
        order.setCouponCode("FLAT50");
        order.setCouponDiscount(3.2);
        order.setTotalCost(120);
        order.setIsActive(Boolean.TRUE);

        Customer customer = new Customer();
        customer.setCustomerName("ajay");
        customer.setAddress("123 Main St");

        Cart cart = new Cart();
        cart.setId(2L);

        CartItem cartItem = new CartItem();
        cartItem.setSubtotal(50.0);
        cartItem.setItemPrice(10.0);
        cartItem.setQuantity(5);

        Menu menu = new Menu();
        Item item = new Item();
        item.setItemName("strawberry ice cream");
        menu.setItem(item);
        menu.setRestaurant(new Restaurant());
        cartItem.setMenu(menu);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        order.setCustomer(customer);
        order.setCart(cart);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        com.thinkpalm.thinkfood.model.Order result = orderService.findOrderById(orderId);

        assertNotNull(result);
        assertEquals("FLAT50", result.getCouponCode());
        assertEquals(3.2, result.getCouponDiscount());
        assertEquals(120.0, result.getTotalCost());
        assertEquals("ajay", result.getCustomerName());
        assertEquals(2L, result.getCartId());
        assertEquals("123 Main St", result.getAddress());

    }

    @Test
    void deleteOrder_ValidId_Success() throws NotFoundException {
        long orderId = 199L;
        Order existingOrder = new Order();
        existingOrder.setIsActive(Boolean.TRUE);
        existingOrder.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        doNothing().when(orderRepository).deleteById(orderId);

        com.thinkpalm.thinkfood.model.Order deletedOrder = orderService.deleteOrder(orderId);

        assertNotNull(deletedOrder);
        assertEquals(existingOrder.getId(), deletedOrder.getId());
    }

    @Test
    void deleteOrderNotFoundExceptionThrown() {
        // Prepare test data
        long nonExistentOrderId = 999L;

        // Mock repository
        when(orderRepository.findById(nonExistentOrderId)).thenReturn(Optional.empty());

        // Perform delete and expect NotFoundException
        assertThrows(NotFoundException.class, () -> orderService.deleteOrder(nonExistentOrderId));
    }

    @Test
    void getOrdersByCustomerId_ValidCustomerId_ReturnOrderList() throws NotFoundException {

        Long customerId = 1L;

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setId(1L);
        order1.setCustomer(new Customer());
        order1.getCustomer().setCustomerName("anusuu");
        order1.setCart(new Cart());
        order1.getCart().setId(100L);
        order1.setCouponCode("FLAT50");
        order1.setCouponDiscount(10.0);
        order1.setIsActive(Boolean.TRUE);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setCustomer(new Customer());
        order2.getCustomer().setCustomerName("anusuu");
        order2.setCart(new Cart());
        order2.getCart().setId(200L);
        order2.setCouponCode("FLAT22");
        order2.setCouponDiscount(20.0);
        order2.setIsActive(Boolean.TRUE);

        orders.add(order1);
        orders.add(order2);

        when(orderRepository.findByCustomerId(customerId)).thenReturn(orders);

        List<com.thinkpalm.thinkfood.model.Order> result = orderService.getOrdersByCustomerId(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        com.thinkpalm.thinkfood.model.Order resultOrder1 = result.get(0);
        assertEquals(1L, resultOrder1.getId());
        assertEquals("anusuu", resultOrder1.getCustomerName());
        assertEquals(100L, resultOrder1.getCartId());
        assertEquals("FLAT50", resultOrder1.getCouponCode());
        assertEquals(10.0, resultOrder1.getCouponDiscount());

        com.thinkpalm.thinkfood.model.Order resultOrder2 = result.get(1);
        assertEquals(2L, resultOrder2.getId());
        assertEquals("anusuu", resultOrder2.getCustomerName());
        assertEquals(200L, resultOrder2.getCartId());
        assertEquals("FLAT22", resultOrder2.getCouponCode());
        assertEquals(20.0, resultOrder2.getCouponDiscount());
    }


    //    @Test
//    void testDeleteOrder() throws NotFoundException {
//        Long orderId = 152L;
//        Order order = new Order();
//        order.setId(orderId);
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//
//        com.thinkpalm.thinkfood.model.Order deletedOrder = orderService.deleteOrder(orderId);
//
//        verify(orderRepository, times(1)).deleteById(orderId);
//        assertEquals(orderId, deletedOrder.getId());
//    }
//
//    @Test
//    void testDeleteOrderOrderNotFound() {
//        Long nonExistentOrderId = 999L;
//
//        when(orderRepository.findById(nonExistentOrderId)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> orderService.deleteOrder(nonExistentOrderId));
//
//        verify(orderRepository, never()).deleteById(nonExistentOrderId);
//    }
    @Test
    void softDeleteOrderById_validId_orderDeletedSuccessfully() throws NotFoundException {

        long orderId = 1L;

        Order order = new Order();
        order.setId(orderId);
        order.setIsActive(true);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        String result = orderService.softDeleteOrderById(orderId);

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(order);

        assertFalse(order.getIsActive());
        assertEquals("Menu With Id " + orderId + " deleted successfully!", result);
    }

    @Test
    void softDeleteOrderById_invalidId_shouldThrowNotFoundException() {
        long invalidOrderId = 99L;

        when(orderRepository.findById(invalidOrderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            orderService.softDeleteOrderById(invalidOrderId);
        });
    }

    @Test
    void softDeleteOrderById_inactiveOrder_shouldThrowNotFoundException() {
        long inactiveOrderId = 2L;

        Order inactiveOrder = new Order();
        inactiveOrder.setId(inactiveOrderId);
        inactiveOrder.setIsActive(false);

        when(orderRepository.findById(inactiveOrderId)).thenReturn(Optional.of(inactiveOrder));

        assertThrows(NotFoundException.class, () -> {
            orderService.softDeleteOrderById(inactiveOrderId);
        });
    }
//    @Test
//    void testGetOrdersByRestaurantId() throws NotFoundException {
//        Long restaurantId = 123L;
//        Integer pageNo = 0;
//        Integer pageSize = 10;
//
//        List<Order> orders = new ArrayList<>();
//        Order order1 = new Order();
//        order1.setId(1L);
//        order1.setCustomer(new Customer());
//        order1.getCustomer().setCustomerName("anusuu");
//        order1.setCart(new Cart());
//        order1.getCart().setId(100L);
//        order1.setCouponCode("FLAT50");
//        order1.setCouponDiscount(10.0);
//        order1.setIsActive(Boolean.TRUE);
//
//        Order order2 = new Order();
//        order2.setId(2L);
//        order2.setCustomer(new Customer());
//        order2.getCustomer().setCustomerName("anusuu");
//        order2.setCart(new Cart());
//        order2.getCart().setId(200L);
//        order2.setCouponCode("FLAT22");
//        order2.setCouponDiscount(20.0);
//        order2.setIsActive(Boolean.TRUE);
//
//        orders.add(order1);
//        orders.add(order2);
//
//
//
//        Page<Order> mockPage = mock(Page.class);
//        when(mockPage.isEmpty()).thenReturn(false);
//        when(mockPage.getContent()).thenReturn(orders);
//
//        when(orderRepository.findByRestaurantId(eq(restaurantId), any(PageRequest.class)))
//                .thenReturn(mockPage);
//
//        List<com.thinkpalm.thinkfood.model.Order> result = orderService.getOrdersByRestaurantId(pageNo, pageSize, restaurantId);
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//
//        com.thinkpalm.thinkfood.model.Order resultOrder1 = result.get(0);
//        assertEquals(1L, resultOrder1.getId());
//        assertEquals("anusuu", resultOrder1.getCustomerName());
//        assertEquals(100L, resultOrder1.getCartId());
//        assertEquals("FLAT50", resultOrder1.getCouponCode());
//        assertEquals(10.0, resultOrder1.getCouponDiscount());
//
//
//
//        verify(orderRepository).findByRestaurantId(eq(restaurantId), any(PageRequest.class));
//    }
}










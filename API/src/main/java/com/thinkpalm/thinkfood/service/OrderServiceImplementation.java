package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.*;
import com.thinkpalm.thinkfood.exception.NotAvailableException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing orders.
 * This class provides methods for creating, updating, retrieving, and deleting orders.
 * author: daan.j
 * version: 2.0
 * since: 31/10/23
 */

@Log4j2
@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;

//    @Autowired
//    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    private static final double gst=10.0;

    /**
     * Creates a new order.
     *
     * @param newOrder The details of the new order.
     * @return The created order.
     * @throws NotFoundException If the order cannot be created.
     */
    @Override
    public com.thinkpalm.thinkfood.model.Order createOrder(com.thinkpalm.thinkfood.model.Order newOrder) throws NotFoundException {

        Order orderEntity = new Order();

        orderEntity.setCouponCode(newOrder.getCouponCode());

        Optional<Coupon> discount1 = couponRepository.findByCouponCode(newOrder.getCouponCode());

        double discountPercentage=0;

        if (discount1.isPresent()) {
            if (discount1.get().getIsActive()) {
                discountPercentage=discount1.get().getDiscountPercentage();
                orderEntity.setCouponId(discount1.get().getId());
                orderEntity.setCouponDiscount(discountPercentage);
            }
            else {
                log.info("check if coupon is valid");
                throw new NotFoundException("Invalid coupon id");
            }
        }
//        else {
//            log.error("Coupon not there with given coupon name");
//            throw new NotFoundException("Coupon not Found");
//        }






        Long cartId = newOrder.getCartId();
        com.thinkpalm.thinkfood.entity.Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart not found"));
        orderEntity.setCart(cart);


        Long customerId = cart.getCustomer().getId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));
        orderEntity.setCustomer(customer);
        orderEntity.setIsActive(true);
        orderRepository.save(orderEntity);

        com.thinkpalm.thinkfood.model.Order orderModel = new com.thinkpalm.thinkfood.model.Order();
        //  orderModel.setCustomerId(orderEntity.getCustomerId());
        orderModel.setId(orderEntity.getId());
        orderModel.setCartId(cartId);
        orderModel.setCouponCode(orderEntity.getCouponCode());
        orderModel.setCouponDiscount(orderEntity.getCouponDiscount());
      //  orderModel.setCustomerId(customerId);
        orderModel.setAddress(customer.getAddress());
        orderModel.setCustomerName(customer.getCustomerName());


        cartId = newOrder.getCartId();
        orderModel.setCartId(cartId);

        List<CartItem> cartItems = cart.getCartItems();


       List<com.thinkpalm.thinkfood.model.CartItem> cartItemModels = new ArrayList<>();
       List<com.thinkpalm.thinkfood.model.OrderedItem> orderedItems = new ArrayList<>();



        double totalAmount = cart.getTotalAmount();

        for (CartItem cartItem : cartItems) {
            // totalAmount += cartItem.getSubtotal();

            Item item = cartItem.getMenu().getItem();

            com.thinkpalm.thinkfood.model.OrderedItem orderedItem = new com.thinkpalm.thinkfood.model.OrderedItem();
            orderedItem.setName(item.getItemName());
            orderedItem.setSubTotal(cartItem.getSubtotal());
            orderedItem.setPrice(cartItem.getItemPrice());
            orderedItem.setQuantity(cartItem.getQuantity());
            orderedItem.setRestaurant(cartItem.getMenu().getRestaurant().getRestaurantName());
            orderedItems.add(orderedItem);
        }
        orderEntity.setRestaurant(cartItems.get(0).getMenu().getRestaurant());


        double discountAmount = (discountPercentage / 100) * totalAmount;
        log.info("calculated the discount amount");
        double totalCost = totalAmount - discountAmount;
        double gstAmt= (gst / 100) * totalCost;
        log.info("calculated the total cost with gst");



        orderEntity.setTotalCost(totalCost+gstAmt);
        orderRepository.save(orderEntity);

        orderModel.setGstAmount(gstAmt);
        orderModel.setTotalCost(totalCost+gstAmt);
        orderModel.setCartItems(cartItemModels);
        orderModel.setOrderedItems(orderedItems);

        return orderModel;
    }

    /**
     * Updates the coupon associated with an order.
     *
     * @param orderId The ID of the order to update.
     * @param newCouponCode The new coupon code.
     * @throws NotFoundException If the order or coupon cannot be found.
     */

    @Override
    public void updateCoupon(Long orderId, String newCouponCode) throws NotFoundException {
        log.info("Entered into update coupon implementation");
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
        if (!order.getIsActive()) {
            throw new NotAvailableException("Invalid order id");
        }

        Optional<Coupon> newCoupon = couponRepository.findByCouponCode(newCouponCode);
        if (newCoupon.isEmpty()) {
            throw new NotFoundException("New Coupon not Found");
        }
        if (!newCoupon.get().getIsActive()) {
            throw new NotFoundException("Invalid coupon id");
        }


        double discountPercentage = newCoupon.get().getDiscountPercentage();

        order.setCouponCode(newCouponCode);
        order.setCouponId(newCoupon.get().getId());
        order.setCouponDiscount(discountPercentage);

        //double totalAmount = calculateTotalAmount(order.getCart().getCartItems());
        double totalAmount = order.getCart().getTotalAmount();
        double discountAmount = (discountPercentage / 100) * totalAmount;
        double totalCost = totalAmount - discountAmount;
        order.setTotalCost(totalCost);

        orderRepository.save(order);
    }
    /**
     * Finds an order by its ID.
     *
     * @param orderId The ID of the order.
     * @return The order information.
     * @throws NotFoundException If the order cannot be found.
     */
    @Override
    public com.thinkpalm.thinkfood.model.Order findOrderById(Long orderId) throws NotFoundException {
        log.info("Entered into find order by id implementation ");
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
        if (!order.getIsActive()) {
            throw new NotFoundException("Invalid order id");
        }


        com.thinkpalm.thinkfood.model.Order orderModel = new com.thinkpalm.thinkfood.model.Order();
      //  orderModel.setCustomerId(order.getCustomer().getId());
        orderModel.setId(orderId);
        orderModel.setCustomerName(order.getCustomer().getCustomerName());
        orderModel.setCartId(order.getCart().getId());
        orderModel.setCouponCode(order.getCouponCode());
        orderModel.setCouponCode(order.getCouponCode());
        orderModel.setCouponDiscount(order.getCouponDiscount());
        orderModel.setTotalCost(order.getTotalCost());
        orderModel.setAddress(order.getCustomer().getAddress());
        orderModel.setPreparationStatus(order.getPreparationStatus());
        orderModel.setDate(order.getCreatedDateTime());
        orderModel.setDeliveryStatus(order.getDeliveryStatus());

        double gstAmt= (gst / 100) * (order.getTotalCost());

        orderModel.setGstAmount(gstAmt);

        List<CartItem> cartItems = order.getCart().getCartItems();
      //  List<com.thinkpalm.thinkfood.model.CartItem> cartItemModels = new ArrayList<>();
        List<com.thinkpalm.thinkfood.model.OrderedItem> orderedItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {

            Item item = cartItem.getMenu().getItem();
            com.thinkpalm.thinkfood.model.OrderedItem orderedItem = new com.thinkpalm.thinkfood.model.OrderedItem();
            orderedItem.setName(item.getItemName());
            orderedItem.setItemImage(item.getImage());
            orderedItem.setSubTotal(cartItem.getSubtotal());
            orderedItem.setQuantity(cartItem.getQuantity());
            orderedItem.setRestaurant(cartItem.getMenu().getRestaurant().getRestaurantName());
            orderedItem.setRestaurantImage(cartItem.getMenu().getRestaurant().getImage());
            orderedItem.setPrice(cartItem.getItemPrice());
            orderedItems.add(orderedItem);
        }

      //  orderModel.setCartItems(cartItemModels);
        orderModel.setOrderedItems(orderedItems);

        return orderModel;
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete.
     * @return The deleted order.
     * @throws NotFoundException If the order cannot be found.
     */

    @Override
    public com.thinkpalm.thinkfood.model.Order deleteOrder(Long id) throws NotFoundException {
        log.info("Entered into delete order implementation");
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty())
        {
            throw new NotFoundException("No Such Order");
        }
        if (!orderOptional.get().getIsActive()) {
            throw new NotFoundException("Invalid order id");
        }
        Order deletedOrder = orderOptional.get();

        com.thinkpalm.thinkfood.model.Order deletedOrderModel = new com.thinkpalm.thinkfood.model.Order();
        BeanUtils.copyProperties(deletedOrder,deletedOrderModel);

        orderRepository.deleteById(id);

        return deletedOrderModel;

    }



    /**
     * Retrieves a list of orders for a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of orders.
     * @throws NotFoundException If no orders are found for the customer.
     */

    @Override
    public List<com.thinkpalm.thinkfood.model.Order> getOrdersByCustomerId(Long customerId) throws NotFoundException {
        List<com.thinkpalm.thinkfood.entity.Order> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            throw new NotFoundException("No orders found for customer with ID: " + customerId);
        }

        List<com.thinkpalm.thinkfood.model.Order> orderModels = new ArrayList<>();

        for (com.thinkpalm.thinkfood.entity.Order order : orders) {
            if (!order.getIsActive()) {
                continue;
            }

            com.thinkpalm.thinkfood.model.Order orderModel = findOrderById(order.getId());
            orderModels.add(orderModel);
        }

        return orderModels;
    }
//    @Override
//    public List<com.thinkpalm.thinkfood.model.Order> getOrdersByCustomerId(Long customerId) throws NotFoundException {
//        log.info("Entered into get orders by customer implementation");
//        List<com.thinkpalm.thinkfood.entity.Order> orders = orderRepository.findByCustomerId(customerId);
//        if (orders.isEmpty()) {
//            throw new NotFoundException("No orders found for customer with ID: " + customerId);
//        }
//
//        List<com.thinkpalm.thinkfood.model.Order> orderModels = new ArrayList<>();
//
//
//
//        for (com.thinkpalm.thinkfood.entity.Order order : orders) {
//            if (!order.getIsActive()) {
//                continue;
//            }
//
//            com.thinkpalm.thinkfood.model.Order orderModel = new com.thinkpalm.thinkfood.model.Order();
//            orderModel.setId(order.getId());
//            orderModel.setCustomerName(order.getCustomer().getCustomerName());
//            orderModel.setCartId(order.getCart().getId());
//            orderModel.setCouponCode(order.getCouponCode());
//            orderModel.setCouponDiscount(order.getCouponDiscount());
//
//            orderModels.add(orderModel);
//        }
//
//        return orderModels;
//    }

    @Override
    public List<com.thinkpalm.thinkfood.model.Order> getOrdersByRestaurantId(Long restaurantId) throws NotFoundException {
        log.info("Entered into get orders by restaurant implementation");

        List<com.thinkpalm.thinkfood.entity.Order> orders = orderRepository.findByRestaurantId(restaurantId);

        if (orders.isEmpty()) {
            throw new NotFoundException("No orders found for restaurant with ID: " + restaurantId);
        }

        List<com.thinkpalm.thinkfood.model.Order> orderModels = new ArrayList<>();

        for (com.thinkpalm.thinkfood.entity.Order order : orders) {
            if (!order.getIsActive()) {
                continue;
            }

            com.thinkpalm.thinkfood.model.Order orderModel = findOrderById(order.getId());
            orderModels.add(orderModel);
        }

        return orderModels;
    }

//    @Override
//    public List<com.thinkpalm.thinkfood.model.Order> getOrdersByRestaurantId(Integer pageNo, Integer pageSize, Long restaurantId) throws NotFoundException {
//        log.info("Entered into get orders by restaurant implementation");
//
//        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
//
//        Page<com.thinkpalm.thinkfood.entity.Order> ordersPage = orderRepository.findByRestaurantId(restaurantId, pageRequest);
//
//        if (ordersPage.isEmpty()) {
//            throw new NotFoundException("No orders found for restaurant with ID: " + restaurantId);
//        }
//
//        List<com.thinkpalm.thinkfood.model.Order> orderModels = new ArrayList<>();
//
//        for (com.thinkpalm.thinkfood.entity.Order order : ordersPage.getContent()) {
//            com.thinkpalm.thinkfood.model.Order orderModel = new com.thinkpalm.thinkfood.model.Order();
//            orderModel.setId(order.getId());
//            orderModel.setCustomerName(order.getCustomer().getCustomerName());
//            orderModel.setCartId(order.getCart().getId());
//            orderModel.setCouponCode(order.getCouponCode());
//            orderModel.setCouponDiscount(order.getCouponDiscount());
//            orderModels.add(orderModel);
//        }
//
//        return orderModels;
//    }

//    @Override
//    public List<com.thinkpalm.thinkfood.model.Order> getOrdersByRestaurantId1(Long restaurantId) throws NotFoundException{
//        List<com.thinkpalm.thinkfood.entity.Order> orders = orderRepository.findByRestaurant1Id(restaurantId);
//        if (orders.isEmpty()) {
//            throw new NotFoundException("No orders found for customer with ID: " + restaurantId);
//        }
//
//        List<com.thinkpalm.thinkfood.model.Order> orderModels = new ArrayList<>();
//
//        for (com.thinkpalm.thinkfood.entity.Order order : orders) {
//            if (!order.getIsActive()) {
//                continue;
//            }
//
//            com.thinkpalm.thinkfood.model.Order orderModel = findOrderById(order.getId());
//            orderModels.add(orderModel);
//        }
//
//        return orderModels;
//    }






    /**
     * Soft delete an order by id.
     * @param id
     * @return
     * @throws NotFoundException
     */

    @Override
    public String softDeleteOrderById(Long id) throws NotFoundException {

        com.thinkpalm.thinkfood.entity.Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu with ID " + id + " not found"));

        if (!order.getIsActive()) {
            throw new NotFoundException("Invalid order id");
        }
        order.setIsActive(false);

        orderRepository.save(order);

        return "Menu With Id "+id+" deleted successfully!";
    }




}



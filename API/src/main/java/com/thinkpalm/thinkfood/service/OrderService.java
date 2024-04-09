package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing orders.
 * This interface defines methods for creating, updating, retrieving, and deleting orders.
 * author: daan.j
 * version: 2.0
 * since: 31/10/23
 */
public interface OrderService {

   /**
    * Creates a new order.
    *
    * @param newOrder The details of the new order.
    * @return The created order.
    * @throws NotFoundException If the order cannot be created.
    */
   Order createOrder(Order newOrder) throws NotFoundException;
   /**
    * Retrieves a list of orders for a specific customer.
    *
    * @param customerId The ID of the customer.
    * @return A list of orders.
    * @throws NotFoundException If no orders are found for the customer.
    */
   List<com.thinkpalm.thinkfood.model.Order> getOrdersByCustomerId(Long customerId) throws NotFoundException;


   /**
    * Updates the coupon associated with an order.
    *
    * @param orderId The ID of the order to update.
    * @param newCouponCode The new coupon code.
    * @throws NotFoundException If the order or coupon cannot be found.
    */
   void updateCoupon(Long orderId, String newCouponCode) throws NotFoundException;

   /**
    * Finds an order by its ID.
    *
    * @param orderId The ID of the order.
    * @return The order information.
    * @throws NotFoundException If the order cannot be found.
    */

   com.thinkpalm.thinkfood.model.Order findOrderById(Long orderId) throws NotFoundException;

   /**
    * Deletes an order by its ID.
    *
    * @param id The ID of the order to delete.
    * @return The deleted order.
    * @throws NotFoundException If the order cannot be found.
    */

   Order deleteOrder(Long id)throws NotFoundException;



   String softDeleteOrderById(Long id) throws NotFoundException;

   //Used earlier
  // public List<com.thinkpalm.thinkfood.model.Order> getOrdersByRestaurantId(Integer pageNo, Integer pageSize, Long restaurantId) throws NotFoundException;

   public List<com.thinkpalm.thinkfood.model.Order> getOrdersByRestaurantId(Long restaurantId) throws NotFoundException;
  //  List<com.thinkpalm.thinkfood.model.Order> getOrdersByRestaurantId1(Long restaurantId) throws NotFoundException;


}




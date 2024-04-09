package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.NotAvailableException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.Order;
import com.thinkpalm.thinkfood.service.EmailService;
import com.thinkpalm.thinkfood.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller class for handling orders.
 * This class is responsible for receiving and sending requests related to orders and
 * interacts with the corresponding service implementation to perform operations.
 * @author daan.j
 * @version 2.0
 * @since 31/10/23
 */

@RestController
@RequestMapping("/think-food/order")
@Log4j2
@SecurityRequirement(name="thinkfood")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);


    @Autowired
    OrderService orderService;

    @Autowired
    private EmailService emailService;


    /**
     * Create a new order.
     *
     * @param newOrder The details of the new order.
     * @return ResponseEntity<Order> The created order with HTTP status.
     * @throws NotFoundException If the order cannot be created.
     */
    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody Order newOrder) throws NotFoundException {
         try {
             log.info("Entered into Create Order Controller");
             Order order =emailService.orderConfirmationEmail(newOrder);
             return ResponseEntity.ok(order);
         }catch (NotFoundException e){
             log.error("Invalid inputs in create order controller " +e.getMessage());
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Inputs");
         }

    }

    /**
     * Update the coupon for a specific order.
     *
     * @param orderId The ID of the order to update.
     * @param newCouponCode The new coupon code.
     * @return ResponseEntity<String> A message indicating the result of the update.
     */

    @PutMapping("/{orderId}/coupon")
    public ResponseEntity<String> updateCoupon(@PathVariable Long orderId, @RequestParam String newCouponCode) {
        try {
            log.info("Entered into Update Coupon Controller");
            orderService.updateCoupon(orderId, newCouponCode);
            return ResponseEntity.ok("Coupon updated successfully");
        }catch (NotAvailableException e)
        {   log.error("Invalid Order Id provided in update coupon");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such order ");
        }
        catch (NotFoundException e) {
            log.error("Invalid Coupon id provided in update Coupon");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such coupon");
        }
    }

    /**
     * Find an order by its ID.
     *
     * @param orderId The ID of the order.
     * @return ResponseEntity<Object> The order information with HTTP status.
     */

    @GetMapping("/{orderId}")
    public ResponseEntity<Object> findOrderById(@PathVariable Long orderId) {
        try {
            log.info("Entered into Find By Order Controller");
            com.thinkpalm.thinkfood.model.Order order = orderService.findOrderById(orderId);
            return ResponseEntity.ok(order);
        } catch (NotFoundException e) {
            log.error("Invalid order id provided in Find By Order");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order ID invalid");
        }
    }

    /**
     * Get a list of orders for a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return ResponseEntity<List<Order>> A list of orders with HTTP status.
     */

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<com.thinkpalm.thinkfood.model.Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        try {
            log.info("Entered into Get Orders by Customer Id Controller");
            List<com.thinkpalm.thinkfood.model.Order> orders = orderService.getOrdersByCustomerId(customerId);
            return ResponseEntity.ok(orders);
        } catch (NotFoundException e) {
            log.error("Invalid customer id provided in Get orders By Customer Id Controller");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Delete an order by its ID.
     *
     * @param id The ID of the order to delete.
     * @return ResponseEntity<String> A message indicating the result of the deletion.
     * @throws NotFoundException If the order cannot be found.
     */

    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<String> deleteItemById(@PathVariable Long id) throws NotFoundException {
        try {
            log.info("Entered into Delete Order Controller");
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Item with ID " + id + " has been deleted.");
        } catch(NotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such id");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> softDeleteOrder(@PathVariable Long id) {
        try {
            String response = orderService.softDeleteOrderById(id);
            log.info("Delete for Order with ID " + id + " was successful.");
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            log.error(" Order not found for ID " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such id");
        }
    }
//    @GetMapping("/restaurant/{restaurantId}")
//    public List<com.thinkpalm.thinkfood.model.Order> getOrdersByRestaurantId(
//            @RequestParam(defaultValue = "0") Integer pageNo,
//            @RequestParam(defaultValue = "5") Integer pageSize,
//            @PathVariable Long restaurantId) throws NotFoundException {
//        return orderService.getOrdersByRestaurantId(pageNo, pageSize, restaurantId);
//    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<com.thinkpalm.thinkfood.model.Order> getOrdersByRestaurantId1(
            @PathVariable Long restaurantId) throws NotFoundException {
        return orderService.getOrdersByRestaurantId(restaurantId);
    }



//


}


//    @GetMapping("/{orderId}")
//    public Order findOrderById(@PathVariable Long orderId) {
//        try {
//            com.thinkpalm.thinkfood.model.Order order = orderService.findOrderById(orderId);
//            return order;
//        } catch (NotFoundException e) {
//            return null;
//        }
//    }





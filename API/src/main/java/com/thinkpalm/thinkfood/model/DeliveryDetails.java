/**
 * Model class for to create a display page for delivery.
 * This class is to get and put data through entity from user to databases.
 * @author: Rinkle Rose Renny
 * @since : 30 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.model;

import com.thinkpalm.thinkfood.entity.Customer;
import com.thinkpalm.thinkfood.entity.Delivery;
import com.thinkpalm.thinkfood.entity.Order;
import com.thinkpalm.thinkfood.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetails {

    /**
     * Id of the delivery details.
     */
    private Long id;
    /**
     * Id of the order is used.
     */
    private Order orderId;
    /**
     * Time taken to deliver food from restaurant to customer.
     */
    private Long timeTaken;
    /**
     * Distance taken from restaurant to customer.
     */
    private Double totalDistance;
    /**
     * Delivery Status is shown here.
     */
    private String deliveryStatus;
    /**
     * Id of restaurant.
     */

    private Restaurant restaurantId;
    /**
     * Id of customer id is used.
     */
    private Customer customerId;
    /**
     * Id of the delivery table is used.
     */
    @ManyToOne
    private Delivery deliveryId;


}



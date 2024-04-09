/**
 * this class is used to display delivery.
 * These are only content that get displayed.
 * @author: Rinkle Rose Renny
 * @since : 2 November 2023
 * @version : 2.0
 */

package com.thinkpalm.thinkfood.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetailsDisplay {
    /**
     * Id of the delivery details.
     */
    private Long id;
    /**
     * Id of the order is used.
     */
    private Long orderId;
    /**
     * Date and Time are displayed.
     */
    private LocalDateTime createdDateTime;
    /**
     * Time taken to deliver food from restaurant to customer.
     */
    private Long timeTaken;
    /**
     * Name of the restaurant.
     */
    private String restaurantName;

    /**
     * Address of the customer.
     */
    private String endAddress;
    /**
     * Distance taken from restaurant to customer.
     */
    private Double totalDistance;
    /**
     * Delivery Status is shown here.
     */
    private String deliveryStatus;

    /**
     * Items are displayed.
     */
    private List<DeliveryItem> deliveryItems;

    /**
     * Name of the customer
     */
    private String customerName;

    /**
     * Name of the deliveryAgent.
     */

    private String deliveryName;


}


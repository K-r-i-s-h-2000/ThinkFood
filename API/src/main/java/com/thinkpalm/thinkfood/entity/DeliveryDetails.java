/**
 * Entity class for to create a display page for delivery.
 * This class is to display page and get required details to display information.
 * @author: Rinkle Rose Renny
 * @since : 30 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="delivery_details")
public class DeliveryDetails extends EntityDoc{

    /**
     * Time taken to deliver food from restaurant to customer.
     */
    @Column(name = "total_time_taken", nullable=false)
    private Long timeTaken;

    /**
     * Distance taken from restaurant to customer.
     */
    @Column(name = "total_distance", nullable=false)
    private Double totalDistance;

    /**
     * Delivery Status is shown here.
     */
    @Column(name="delivery_status", nullable=false,length=100)
    private String deliveryStatus = String.valueOf(PreparationStatus.PREPARATION);

    /**
     * Id of the order is used.
     */
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order orderId;

    /**
     * Id of the delivery table is used.
     */
    @OneToOne
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    private Delivery deliveryId;

    /**
     * Id of customer id is used.
     */

    @OneToOne
    @JoinColumn(name="customer_id", referencedColumnName = "id")
    private Customer customerId;

    /**
     * Id of restaurant.
     */

    @OneToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurantId;
}

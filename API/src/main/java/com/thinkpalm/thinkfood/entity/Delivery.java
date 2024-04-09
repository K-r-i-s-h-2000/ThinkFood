/**
 * Entity class for delivery agents CRUD operations.
 * This class is to make modification to the table mentioned.
 * @author: Rinkle Rose Renny
 * @since : 26 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="delivery")
@Entity
public class Delivery extends EntityDoc
{
    /**
    * Name of the delivery agent
     */
    @Column(name = "delivery_name",nullable = false,length = 100)
    private String deliveryName;

    /**
     * Contact Number of the delivery agent
     */
    @Column(name = "delivery_contact_details",nullable = false)
    private String deliveryContactNumber;

    /**
     * Vehicle Number of the vehicle used by  the delivery agent
     */
    @Column(name = "vehicle_number", nullable = false, length = 100)
    private String vehicleNumber;

    @Builder.Default
    @Column(name="is_active",columnDefinition = "default 'FALSE'")
    private Boolean isActive=false;

    /**
     * Availability of the delivery agent.
     * Availability = OPEN, then delivery agent is available.
     * Availability = CLOSE, then delivery agent is not available.
     */
    @Column(name = "delivery_availability",nullable=false,columnDefinition = "varchar(100) default 'OPEN'",length=100)
    private String deliveryAvailability;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}

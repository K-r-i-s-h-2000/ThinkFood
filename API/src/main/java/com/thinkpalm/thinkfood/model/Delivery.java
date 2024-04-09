/**
 * Model class for delivery agents CRUD operations.
 * This class is to get and put data through entity from user to databases.
 * @author: Rinkle Rose Renny
 * @since : 26 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.model;

import com.thinkpalm.thinkfood.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Delivery {
    /**
     * I'd of the delivery agent
     */
    private Long id;
    /**
     * Name of the delivery agent
     */
    private String deliveryName;
    /**
     * Contact Number of the delivery agent
     */
    private String deliveryContactNumber;
    /**
     * Vehicle Number of the vehicle used by  the delivery agent
     */
    private String vehicleNumber;
    /**
     * Availability of the delivery agent.
     * Availability = OPEN, then delivery agent is available.
     * Availability = CLOSE, then delivery agent is not available.
     * Availability = OPEN is assigned as default.
     */
    private String deliveryAvailability="OPEN";
    private User user;
    /**
     * The email address of the user, which serves as their unique identifier.
     */
    private String email;
}

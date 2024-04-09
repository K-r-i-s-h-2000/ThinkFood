/**
 * This is a service class which is used to calculate location corresponding to the requirements.
 * @author: Rinkle Rose Renny
 * @since : 30 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.CustomerNotFoundException;
import com.thinkpalm.thinkfood.exception.MenuNotFoundException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.exception.RestaurantNotFoundException;
import com.thinkpalm.thinkfood.model.DeliveryDetails;
import com.thinkpalm.thinkfood.model.DeliveryDetailsDisplay;
import lombok.extern.log4j.Log4j2;

import java.util.List;

public interface DeliveryDetailsService {

    /**
     * This is to add details to table for future reference.
     * @return a string which shows the function was successful.
     */
    public boolean assignDeliveryByOrder(Long orderId);

    /**
     //     * This function enter details to delivery table and display essential information to users and delivery agents.
     //     * @param pageNo contain Number in which it should start display.
     //     * @param pageSize contains value of number of records that need to be displayed.
     //     * @return delivery details that is used by customer and delivery.
     //     */
    public DeliveryDetailsDisplay displayDeliveryDetailsByOrder(long id);

    public DeliveryDetailsDisplay displayDeliveryDetailsByDeliveryId(long deliveryId);
    public List<DeliveryDetailsDisplay> displayDeliveryDetailsByDeliveryIdList(long deliveryId);

    /**
     * To display total number of orders delivered
     * @param deliveryId is delivery if of agent
     * @return total count
     */

    public int orderCount(Long deliveryId);

    /**
     * This function to update the status of availability of one delivery agent and delivery.
     * If the agent is available,this function will update it to not available automatically and vice versa.
     * @return a string that update is successfully.
     */

    public Boolean updateDeliveryDetailsByName(Long deliveryId);

    public Boolean updateRestaurantPreparationStatus(Long orderId);
}

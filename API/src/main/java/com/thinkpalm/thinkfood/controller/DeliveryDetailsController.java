/**
 *Controller class to display delivery details.
 * This class is to receive and send requests to and from users to perform corresponding functions defined in service implementation.
 * @author: Rinkle Rose Renny
 * @since : 2 November 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.CustomerNotFoundException;
import com.thinkpalm.thinkfood.exception.MenuNotFoundException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.exception.RestaurantNotFoundException;
import com.thinkpalm.thinkfood.model.DeliveryDetails;
import com.thinkpalm.thinkfood.model.DeliveryDetailsDisplay;
import com.thinkpalm.thinkfood.service.DeliveryDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.flogger.Flogger;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/think-food/delivery-details")
@SecurityRequirement(name="thinkfood")
public class DeliveryDetailsController {
    @Autowired
    private DeliveryDetailsService deliveryDetailsService;

    /**
     * This function enter details to delivery table.
     * @return delivery details that is used by customer and delivery.
     */
@PostMapping("/add/{orderId}")
public boolean addToTable(@PathVariable Long orderId) throws NotFoundException {
    log.info("Request to assign delivery agents to orders and add delivery details to table.");
    return deliveryDetailsService.assignDeliveryByOrder(orderId);
}
    /**
     * This function enter details to delivery table and display essential information to users and delivery agents.
     * @return delivery details that is used by customer and delivery.
     */
    @GetMapping("/display/{id}")
    public DeliveryDetailsDisplay displayedDeliveryDetails(@PathVariable Long id)
    {
        log.info("Display details of delivery of orders");
        return deliveryDetailsService.displayDeliveryDetailsByOrder(id);
    }

    @GetMapping("/display-current/{deliveryId}")
    public DeliveryDetailsDisplay deliveryDetailsDisplayByDeliveryId(@PathVariable Long deliveryId)
    {
        log.info("Display details of delivery of orders");
        return deliveryDetailsService.displayDeliveryDetailsByDeliveryId(deliveryId);
    }

    @GetMapping("/display-history/{deliveryId}")
    public List<DeliveryDetailsDisplay> deliveryDetailsDisplayByDeliveryIdList(@PathVariable Long deliveryId)
    {
        log.info("Display details of delivery of orders");
        return deliveryDetailsService.displayDeliveryDetailsByDeliveryIdList(deliveryId);
    }

    @GetMapping("/total-count/{deliveryId}")
    public  int countOrders(@PathVariable Long deliveryId)
    {
        return deliveryDetailsService.orderCount(deliveryId);
    }


    /**
     * This function to update the status of availability of one delivery agent and delivery.
     * If the agent is available,this function will update it to not available automatically and vice versa.
     * @return a string that update is successfully.
     */
    @PutMapping("/update/{id}")
    public Boolean updatedDeliveryDetailsByName(@PathVariable Long id)
    {
        log.info("Received request to update delivery of orders through order delivery id {}", id);
        return deliveryDetailsService.updateDeliveryDetailsByName(id);
    }

    @PutMapping("/update-restaurant-status/{orderId}")
    public  Boolean updateStatusByRestaurantPreparationStatus(@PathVariable Long orderId)
    {
       return  deliveryDetailsService.updateRestaurantPreparationStatus(orderId);
    }

}

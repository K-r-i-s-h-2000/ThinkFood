/**
 *Controller class for delivery agents CRUD operations.
 * This class is to receive and send requests to and from users to perform corresponding functions defined in service implementation.
 * @0author: Rinkle Rose Renny
 * @since : 26 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.model.Delivery;
import com.thinkpalm.thinkfood.service.DeliveryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jdk.jfr.Label;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/think-food/delivery")
@SecurityRequirement(name="thinkfood")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;


    /**
     * @param delivery which is the input from user to insert into table.
     * @return a string which shows whether the function is successful or not.
     */
    @PostMapping("/create")
    public String createDelivery(@RequestBody Delivery delivery)
    {
        log.info("Received request to create delivery with details given.");

        return deliveryService.createDelivery(delivery);
    }

    @GetMapping("/get-delivery-agent/{deliveryId}")
    public Delivery getAgents(@PathVariable Long deliveryId)
    {
        log.info("Received request to display all agent details using pagination");
        return deliveryService.getAgentDetails( deliveryId);
    }

    /**
     * @param Id which is used to find record of the corresponding delivery agent.
     * @param delivery which is the input from user to insert into table.
     * @return  a string which shows whether the function is successful or not.
     */
    @PutMapping("/update/{Id}")
    public Boolean updateDelivery(@PathVariable Long Id, @RequestBody Delivery delivery)
    {
        log.info("Received request to update delivery agents details through accepting thier id{}",Id);
        return deliveryService.updateDelivery(Id,delivery);
    }


    /**
     * @param deliveryId mentions id of agent whose status has to be updated.
     * @return a string which shows whether the function is successful or not.
     */
    @PutMapping("/update-status/{deliveryId}")
    public Boolean updateStatusAvailability(@PathVariable Long deliveryId)
    {
        log.info("Received request to update status of availability of delivery agent of id {} ",deliveryId);
        return deliveryService.updateStatusAvailability(deliveryId);
    }


    /**
     * @param id which is used to find record of the corresponding delivery agent.
     * @return  a string which shows whether the function is successful or not.
     */
    @DeleteMapping("/soft-delete/{id}")
    public boolean softDeleteItem(@PathVariable Long id)
    {
        return deliveryService.softDelete(id);

    }
}

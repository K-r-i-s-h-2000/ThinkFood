/**
 * Service interface for delivery agents CRUD operations.
 * This interface contains abstract functions to perform operations on databases.
 * @author: Rinkle Rose Renny
 * @since : 26 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.DetailsMissingException;
import com.thinkpalm.thinkfood.exception.DetailsNotFoundException;
import com.thinkpalm.thinkfood.model.Delivery;

import java.util.List;

public interface DeliveryService {



    /**
     * This abstract function is used to create a record of a delivery agent.
     * This reads name, contact number, vehicle number which are not null fields and reads availability which is not necessary to be set.
     * availability is set to a default value if input is not given explicitly.
     * @param delivery contains input to insert a record into the table.
     * @return a string that delivery is created successfully.
     * @throws DetailsMissingException when all not nullable fields are not entered.
     */
    public String createDelivery(Delivery delivery);

    /**
     * This function is to display record in the table corresponding to that id.
     * @return A record that is displayed using pagination
     */
    public Delivery getAgentDetails(Long deliveryId);

    /**
     * This function updates the fields of entity record of the delivery agent using id.
     * @param id used to get the entity record details of the delivery agent of specified id
     * @param delivery contains input of fields that need to be updated in to the table.
     * @return  a string which shows if the function operations is successful or not.
     * @throws DetailsMissingException if the record of that specified id is not found.
     */
    public Boolean updateDelivery(Long id, Delivery delivery);


    /**
     * This function update the status of availability of delivery agent.
     * If the agent is available,this function will update it to not available automatically and vice versa.
     * @param deliveryId mentions name of agent whose status has to be updated.
     * @return a string which shows whether the function is successful or not.
     * @throws DetailsNotFoundException if the record of that specified id is not found.
     */
    public Boolean updateStatusAvailability(Long deliveryId);

    /**
     * This function delete the fields of entity record of the delivery agent using id.
     * @param id used to get the entity record details of the delivery agent of specified id
     * @return a string which shows if the function operations is successful or not.
     * @throws DetailsMissingException if the record of that specified id is not found.
     */
    boolean softDelete(Long id);
}

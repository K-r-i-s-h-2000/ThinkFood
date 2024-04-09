/**
 * Service Implementation class for delivery agents CRUD operations.
 * This class contains implemented functions to perform operations on databases.
 * @author: Rinkle Rose Renny
 * @since : 26 October 2023
 * @version : 2.0
 */
package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.User;
import com.thinkpalm.thinkfood.exception.DetailsMissingException;
import com.thinkpalm.thinkfood.exception.DetailsNotFoundException;
import com.thinkpalm.thinkfood.model.Delivery;
import com.thinkpalm.thinkfood.repository.DeliveryRepository;
import com.thinkpalm.thinkfood.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Log4j2
@Service
public class DeliveryServiceImplementation implements DeliveryService{


    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private UserRepository userRepository;



    /**
     * This function is used to create a record of a delivery agent.
     * This reads name, contact number, vehicle number which are not null fields and reads availability which is not necessary to be set.
     * availability is set to a default value if input is not given explicitly.
     * @param delivery contains input to insert a record into the table.
     * @return a string that delivery is created successfully.
     * @throws DetailsMissingException when all not nullable fields are not entered.
     */
    @Override
    public String createDelivery(Delivery delivery) {
        if((delivery.getDeliveryName()==null)||(delivery.getDeliveryContactNumber()==null)||(delivery.getVehicleNumber()==null))
            throw new DetailsMissingException("All details should be entered");

        com.thinkpalm.thinkfood.entity.Delivery deliveryEntity= new com.thinkpalm.thinkfood.entity.Delivery();
        deliveryEntity.setDeliveryName(delivery.getDeliveryName());
        deliveryEntity.setDeliveryContactNumber(delivery.getDeliveryContactNumber());
        deliveryEntity.setVehicleNumber(delivery.getVehicleNumber());
        deliveryEntity.setDeliveryAvailability(delivery.getDeliveryAvailability());
        deliveryEntity.setIsActive(true);
        deliveryEntity=deliveryRepository.save(deliveryEntity);
        log.info("Delivery agent details are added");
        return "Delivery is Created";
    }

    /**
     * This function is to display records in the table.
     * @return A record that is displayed using pagination
     */
    @Override
    public Delivery getAgentDetails(Long deliveryId)
    {
        log.info("To get all agent details from the table  and display agent details");
        Optional<com.thinkpalm.thinkfood.entity.Delivery> deliveryEntity = deliveryRepository.findById(deliveryId);
        if(deliveryEntity.isEmpty())
        {
            throw new DetailsNotFoundException("Details Not found exception.");
        }
        if(!deliveryEntity.get().getIsActive())
        {
            throw new DetailsNotFoundException("Details are not found!");
        }
        Delivery deliveryModel=new Delivery();
        if(deliveryEntity.get().getIsActive()){
            deliveryModel.setId(deliveryEntity.get().getId());
            deliveryModel.setDeliveryName(deliveryEntity.get().getDeliveryName());
            deliveryModel.setDeliveryContactNumber(deliveryEntity.get().getDeliveryContactNumber());
            deliveryModel.setVehicleNumber(deliveryEntity.get().getVehicleNumber());
            deliveryModel.setDeliveryAvailability(deliveryEntity.get().getDeliveryAvailability());
            deliveryModel.setEmail(deliveryEntity.get().getUser().getEmail());
        }
        log.info("Displayed details of delivery agent");
        return deliveryModel;
    }


    /**
     * This function updates the fields of entity record of the delivery agent using id.
     * @param id used to get the entity record details of the delivery agent of id
     * @param delivery contains input of fields that need to be updated in to the table.
     * @return  a string which shows if the function operations is successful or not.
     * @throws DetailsMissingException if the record of that specified id is not found.
     */
    @Override
    public Boolean updateDelivery(Long id, Delivery delivery)  {

        Optional<com.thinkpalm.thinkfood.entity.Delivery> deliveryEntityOptional;
        try {
            deliveryEntityOptional = deliveryRepository.findById(id);
        }
        catch(Exception e)
        {
            log.error("Error in accessing delivery table!");
            throw new DetailsMissingException("Exception handled while accessing details from delivery table.");
        }
        com.thinkpalm.thinkfood.entity.Delivery deliveryEntity = null;
        if (deliveryEntityOptional.isPresent() && deliveryEntityOptional.get().getIsActive()) {
            deliveryEntity = deliveryEntityOptional.get();
        }
        else {
            log.error("Delivery details are not found");
            throw new DetailsNotFoundException("Delivery details Not Found!!");
        }
        if (delivery.getVehicleNumber() != null)
            deliveryEntity.setVehicleNumber(delivery.getVehicleNumber());
        if (delivery.getDeliveryContactNumber() != null)
            deliveryEntity.setDeliveryContactNumber(delivery.getDeliveryContactNumber());
        try {
            deliveryEntity=deliveryRepository.save(deliveryEntity);
        }
        catch(Exception e)
        {
            log.error("Error in accessing delivery table!");
            throw new DetailsMissingException("Exception handled while entering details to delivery table.");
        }

        log.info("Delivery agents details are updated");

        return true;
    }

    /**
     * This function update the status of availability of delivery agent.
     * If the agent is available,this function will update it to not available automatically and vice versa.
     * @param deliveryId mentions id of agent whose status has to be updated.
     * @return a string which shows whether the function is successful or not.
     * @throws DetailsNotFoundException if the record of that specified id is not found.
     */
    @Override
    public Boolean updateStatusAvailability(Long deliveryId) {
        log.info("To update delivery agents availability of id {} ", deliveryId);
        Optional<com.thinkpalm.thinkfood.entity.Delivery> deliveryEntityOptional = deliveryRepository.findById(deliveryId);
        com.thinkpalm.thinkfood.entity.Delivery deliveryEntity=new com.thinkpalm.thinkfood.entity.Delivery();
        if(deliveryEntityOptional.isPresent())
        {

            deliveryEntity = deliveryEntityOptional.get();
            if(deliveryEntity.getIsActive()){
        if("OPEN".equals(deliveryEntity.getDeliveryAvailability()))
        {
            deliveryEntity.setDeliveryAvailability("CLOSE");
        }
        else
        {
            deliveryEntity.setDeliveryAvailability("OPEN");
        }
        deliveryEntity=deliveryRepository.save(deliveryEntity);
        }
        else{
        log.info("Delivery Details Not found");
        return false;}
        }
        else
        {
            log.error("Details of delivery agent of id {} is not found", deliveryId);
            throw new DetailsNotFoundException("Delivery details Not Found!!");
        }
        log.info("Status is updated. ");
        return true;
    }

    /**
     * This function delete the fields of entity record of the delivery agent using id.
     * @param id used to get the entity record details of the delivery agent of specified id
     * @return a string which shows if the function operations is successful or not.
     * @throws DetailsMissingException if the record of that specified id is not found.
     */
    @Override
    public boolean softDelete(Long id)
    {
        Optional<com.thinkpalm.thinkfood.entity.Delivery> deliveryEntityOptional=deliveryRepository.findById(id);
        if(deliveryEntityOptional.isPresent())
        {
            if(deliveryEntityOptional.get().getIsActive()) {
                com.thinkpalm.thinkfood.entity.Delivery deliveryEntity = new com.thinkpalm.thinkfood.entity.Delivery();
                deliveryEntity = deliveryEntityOptional.get();
                if (deliveryEntity.getIsActive()) {
                    deliveryEntity.setIsActive(false);
                    deliveryRepository.save(deliveryEntity);
                    log.info("Deleting from user table!");
                    User user = deliveryEntity.getUser();
                    if(user.getIsActive())
                    {
                        user.setIsActive(false);
                        userRepository.save(user);
                        log.info("User deletion is successful");

                    }
                    else {
                        log.error("User is delted before.");
                        throw new DetailsMissingException("Details are Missing.");
                    }
                } else {
                    log.info("Delivery Details Not found");
                }
            }
            else {
                throw new DetailsMissingException("Details are missing! The record is already delted!");
            }

        }
        else
        {
            log.error("Details of delivery agent of id {} is not found", id);
            throw new DetailsNotFoundException("Delivery details Not Found!!");
        }
        log.info("Delete is done");
        return true;
    }


}


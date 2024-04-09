/**
 * This is a service class which is used to calculate location corresponding to the requirements.
 * @author: Rinkle Rose Renny
 * @since : 30 October 2023
 * @version : 2.0
 */

package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.PreparationStatus;
import com.thinkpalm.thinkfood.exception.*;
import com.thinkpalm.thinkfood.model.*;
import com.thinkpalm.thinkfood.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Service
public class DeliveryDetailsServiceImplementation implements DeliveryDetailsService {

    @Autowired
    private DeliveryDetailsRepository deliveryDetailsRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private  RestaurantRepository restaurantRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderService orderService;

    /**
     * This is to add details to table for future reference.
     * @return a string which shows the function was successful.
     */
    public boolean assignDeliveryByOrder(Long orderId) {
        Optional<com.thinkpalm.thinkfood.entity.DeliveryDetails> deliveryDetailsRepo=deliveryDetailsRepository.findAllByOrder_Id(orderId);
        List<com.thinkpalm.thinkfood.entity.Delivery> deliveryEntity=null;
        try{
            ; deliveryEntity = deliveryRepository.findAll();
        }
        catch(Exception e)
        {
            throw new DetailsNotFoundException("Delivery agent detals are in complete!");
        }

        if(deliveryDetailsRepo.isEmpty()) {
            Long id = null;
            log.info("Finding all details from delivery agent table.");
            if (deliveryEntity.isEmpty()) {
                throw new DetailsNotFoundException("All agents are busy! Please wait for some time!");
            }
            log.info("Found all details from delivery agent table.");
            for (com.thinkpalm.thinkfood.entity.Delivery delivery : deliveryEntity) {
                if ("OPEN".equals(delivery.getDeliveryAvailability())) {
                    id = delivery.getId();
                    delivery.setDeliveryAvailability("CLOSE");
                    break;
                }
            }
            if (id == null) {
                return false;
            }
            Optional<com.thinkpalm.thinkfood.entity.Delivery> deliveryDetailsEntity = deliveryRepository.findById(id);
            if (deliveryDetailsEntity.isEmpty()) {
                throw new DetailsNotFoundException("Delivery details are not present now!");
            }
            log.info("Assigned a delivery agent to deliver orders.");
            DeliveryDetails deliveryDetailsModel = new DeliveryDetails();
            Optional<com.thinkpalm.thinkfood.entity.Order> orderEntity = orderRepository.findById(orderId);
            if (orderEntity.isPresent()) {
                if ("NOT DONE".equals(orderEntity.get().getDeliveryStatus())) {
                    deliveryDetailsModel.setDeliveryId(deliveryDetailsEntity.get());
                    Optional<com.thinkpalm.thinkfood.entity.Cart> cartEntity = cartRepository.findById(orderEntity.get().getCart().getId());
                    if (cartEntity.isPresent()) {
                        log.info("Cart details are accessed through id {}", orderEntity.get().getCart().getId());
                        deliveryDetailsModel.setCustomerId(cartEntity.get().getCustomer());
                        log.info("Customer is added to database");
                        List<com.thinkpalm.thinkfood.entity.CartItem> cartItemEntity = cartItemRepository.findAllByCartId(cartEntity.get().getId());
                        Long menuId = null;
                        for (com.thinkpalm.thinkfood.entity.CartItem items : cartItemEntity) {
                            menuId = items.getMenu().getId();
                            break;
                        }
                        if (menuId != null) {
                            Optional<com.thinkpalm.thinkfood.entity.Menu> menuEntity = menuRepository.findById(menuId);
                            if (menuEntity.isPresent()) {
                                log.info("Menu details are accessed through id {} ", id);
                                Optional<com.thinkpalm.thinkfood.entity.Restaurant> restaurantEntity = restaurantRepository.findById(menuEntity.get().getRestaurant().getId());
                                if (restaurantEntity.isPresent()) {
                                    log.info("Restaurant details are accessed through id {} ", menuEntity.get().getRestaurant().getId());
                                    deliveryDetailsModel.setRestaurantId(restaurantEntity.get());
                                    log.info("Restaurant is added to table.");
                                    Optional<com.thinkpalm.thinkfood.entity.Customer> customerEntity = customerRepository.findById(cartEntity.get().getCustomer().getId());
                                    if (customerEntity.isPresent()) {
                                        log.info("Customer details are accessed through id {} ", cartEntity.get().getCustomer().getId());
                                        Double startLatitude = restaurantEntity.get().getRestaurantLatitude();
                                        Double startLongitude = restaurantEntity.get().getRestaurantLongitude();
                                        Double endLatitude = customerEntity.get().getLatitude();
                                        Double endLongitude = customerEntity.get().getLongitude();
                                        log.info("To calculate distance of the restaurant and customer");
                                        startLatitude = Math.toRadians(startLatitude);
                                        startLongitude = Math.toRadians(startLongitude);
                                        endLatitude = Math.toRadians(endLatitude);
                                        endLongitude = Math.toRadians(endLongitude);
                                        double distanceLatitude = endLatitude - startLatitude;
                                        double distanceLongitude = endLongitude - startLongitude;
                                        double distanceConversion = Math.pow(Math.sin(distanceLatitude / 2), 2)
                                                + Math.cos(startLatitude) * Math.cos(endLatitude)
                                                * Math.pow(Math.sin(distanceLongitude / 2), 2);
                                        double distance = 2 * Math.asin((Math.sqrt(distanceConversion)));
                                        double radius = 6371;
                                        double totalDistance = (distance * radius) / 515;
                                        log.info("Calculated the distance. ");
                                        deliveryDetailsModel.setTotalDistance(totalDistance);
                                        Long timeTaken = menuEntity.get().getPreparationTime() + 5 * Math.round(totalDistance);
                                        deliveryDetailsModel.setTimeTaken(timeTaken);
                                    } else {
                                        log.error("Customer Details of id {} are not found.", cartEntity.get().getCustomer().getId());
                                        throw new NotFoundException("Customer not found!!");
                                    }

                                } else {
                                    log.error("Restaurant Details of id {} are not found.", menuEntity.get().getRestaurant().getId());
                                    throw new NotFoundException("Restaurant Details is not found. ");
                                }
                            } else {
                                log.error("Menu Details of id {} are not found.", id);
                                throw new NotFoundException("Menu details are not found.");
                            }
                        } else {
                            log.error("Menu Details of id {} are not found.", id);
                            throw new NotFoundException("Menu details are not found.");
                        }

                    } else {
                        log.error("Cart Details of id {} are not found.", orderEntity.get().getCart().getId());
                        throw new NotFoundException("Cart Details are not Found exception");
                    }
                    deliveryDetailsModel.setOrderId(orderEntity.get());
                    orderEntity.get().setDeliveryStatus("DONE");
                    orderRepository.save(orderEntity.get());
                    com.thinkpalm.thinkfood.entity.DeliveryDetails deliveryDetailsEntitySet = new com.thinkpalm.thinkfood.entity.DeliveryDetails();
                    deliveryDetailsEntitySet.setDeliveryId(deliveryDetailsModel.getDeliveryId());
                    deliveryDetailsEntitySet.setOrderId(deliveryDetailsModel.getOrderId());
                    deliveryDetailsEntitySet.setCustomerId(deliveryDetailsModel.getCustomerId());
                    deliveryDetailsEntitySet.setRestaurantId(deliveryDetailsModel.getRestaurantId());
                    deliveryDetailsEntitySet.setTimeTaken(deliveryDetailsModel.getTimeTaken());
                    deliveryDetailsEntitySet.setTotalDistance(deliveryDetailsModel.getTotalDistance());
                    deliveryDetailsRepository.save(deliveryDetailsEntitySet);
                }
            } else {
                throw new DetailsNotFoundException("Order Details are not found!!");
            }
        }
        return true;
    }
    /**
     //     * This function enter details to delivery table and display essential information to users and delivery agents.
     //     * @param pageNo contain Number in which it should start display.
     //     * @param pageSize contains value of number of records that need to be displayed.
     //     * @return delivery details that is used by customer and delivery.
     //     */
    public DeliveryDetailsDisplay displayDeliveryDetailsByOrder(long id)
    {
        DeliveryDetailsDisplay deliveryDetails=new DeliveryDetailsDisplay();
        Optional<com.thinkpalm.thinkfood.entity.DeliveryDetails> deliveryDetailsEntity=deliveryDetailsRepository.findAllByOrder_Id(id);
        log.info("Found details of delivery details added in table.");
        if(deliveryDetailsEntity.isPresent())
        {
                deliveryDetails.setId(deliveryDetailsEntity.get().getId());
                deliveryDetails.setRestaurantName(deliveryDetailsEntity.get().getRestaurantId().getRestaurantName());
                deliveryDetails.setDeliveryName(deliveryDetailsEntity.get().getDeliveryId().getDeliveryName());
                deliveryDetails.setCustomerName(deliveryDetailsEntity.get().getCustomerId().getCustomerName());
                deliveryDetails.setEndAddress(deliveryDetailsEntity.get().getCustomerId().getAddress());
                deliveryDetails.setOrderId(deliveryDetailsEntity.get().getOrderId().getId());
                deliveryDetails.setCreatedDateTime(deliveryDetailsEntity.get().getCreatedDateTime());
                deliveryDetails.setTimeTaken(deliveryDetailsEntity.get().getTimeTaken());
                deliveryDetails.setTotalDistance(deliveryDetailsEntity.get().getTotalDistance());
                deliveryDetails.setDeliveryStatus(deliveryDetailsEntity.get().getDeliveryStatus());
                Order OrderById=new Order();
                try {
                    List<DeliveryItem> deliveryItems=new ArrayList<>();
                    OrderById=orderService.findOrderById(deliveryDetailsEntity.get().getOrderId().getId());
                    OrderById.getOrderedItems().forEach(orderedItem -> {
                        DeliveryItem item=new DeliveryItem();
                        item.setItemName(orderedItem.getName());
                        item.setQuantity(orderedItem.getQuantity());
                        deliveryItems.add(item);
                    });
                    deliveryDetails.setDeliveryItems(deliveryItems);
                } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                }
        }
        log.info("Displayed delivery details.");
        return deliveryDetails;
    }

    /**
     //     * This function enter details to delivery table and display essential information to users and delivery agents.
     //     * @return delivery details that is used by customer and delivery.
     //     */
    public DeliveryDetailsDisplay displayDeliveryDetailsByDeliveryId(long deliveryId)
    {
        int countDetails =0;
        log.info("Displays Order delivery details ");
        DeliveryDetailsDisplay deliveryDetails=new DeliveryDetailsDisplay();
        log.info("Delivery Details of delivery agent to find");
        List<com.thinkpalm.thinkfood.entity.DeliveryDetails> deliveryDetailsEntityList=deliveryDetailsRepository.findAllByDelivery_Id(deliveryId);
        log.info("Delivery Details of delivery agent are found");
         com.thinkpalm.thinkfood.entity.DeliveryDetails deliveryDetailsEntity=new com.thinkpalm.thinkfood.entity.DeliveryDetails();
         deliveryDetailsEntityList.forEach(delivery->{
            if(!("DELIVERED".equals(delivery.getDeliveryStatus())))
            {
                log.info("Delivery Status: {}", delivery.getDeliveryStatus());
                log.info("IsActive: {}", delivery.getDeliveryId().getIsActive());
                log.info("Delivery ID: {}", delivery.getDeliveryId().getId());
                log.info("Delivery is equal to given id: {}",delivery.getDeliveryId().getId()==deliveryId);
                if(delivery.getDeliveryId().getIsActive()){
                if(delivery.getDeliveryId().getId()==deliveryId)
                {

                    log.info("assinging it to deliveryDetailsEntity");
                    deliveryDetailsEntity.setDeliveryId(delivery.getDeliveryId());
                    deliveryDetailsEntity.setDeliveryStatus(delivery.getDeliveryStatus());
                    deliveryDetailsEntity.setCustomerId(delivery.getCustomerId());
                    deliveryDetailsEntity.setTimeTaken(delivery.getTimeTaken());
                    deliveryDetailsEntity.setOrderId(delivery.getOrderId());
                    deliveryDetailsEntity.setTotalDistance(delivery.getTotalDistance());
                    deliveryDetailsEntity.setRestaurantId(delivery.getRestaurantId());
                    deliveryDetailsEntity.setId(delivery.getId());
                    deliveryDetailsEntity.setCreatedDateTime(delivery.getCreatedDateTime());
                }}
            }
        });
         if(deliveryDetailsEntity.getId()!=null) {
             deliveryDetails.setId(deliveryDetailsEntity.getId());
             deliveryDetails.setRestaurantName(deliveryDetailsEntity.getRestaurantId().getRestaurantName());
             deliveryDetails.setDeliveryName(deliveryDetailsEntity.getDeliveryId().getDeliveryName());
             deliveryDetails.setCustomerName(deliveryDetailsEntity.getCustomerId().getCustomerName());
             deliveryDetails.setEndAddress(deliveryDetailsEntity.getCustomerId().getAddress());
             deliveryDetails.setOrderId(deliveryDetailsEntity.getOrderId().getId());
             deliveryDetails.setCreatedDateTime(deliveryDetailsEntity.getCreatedDateTime());
             deliveryDetails.setTimeTaken(deliveryDetailsEntity.getTimeTaken());
             deliveryDetails.setTotalDistance(deliveryDetailsEntity.getTotalDistance());
             deliveryDetails.setDeliveryStatus(deliveryDetailsEntity.getDeliveryStatus());
             Order OrderById = new Order();
             try {
                 List<DeliveryItem> deliveryItems = new ArrayList<>();
                 OrderById = orderService.findOrderById(deliveryDetailsEntity.getOrderId().getId());
                 OrderById.getOrderedItems().forEach(orderedItem -> {
                     DeliveryItem item = new DeliveryItem();
                     item.setItemName(orderedItem.getName());
                     item.setQuantity(orderedItem.getQuantity());
                     deliveryItems.add(item);
                 });
                 deliveryDetails.setDeliveryItems(deliveryItems);
             } catch (NotFoundException e) {
                 throw new RuntimeException(e);
             }
         }
         else{
             return null;
         }
        log.info("Displayed delivery details.");
        return deliveryDetails;
    }

    /**
     //     * This function enter details to delivery table and display essential information to users and delivery agents.
     //     * @return delivery details that is used by customer and delivery.
     //     */
    public List<DeliveryDetailsDisplay> displayDeliveryDetailsByDeliveryIdList(long deliveryId)
    {
        log.info("Displays Order delivery details ");

        List<com.thinkpalm.thinkfood.entity.DeliveryDetails> deliveryDetailsEntityList=deliveryDetailsRepository.findAllByDelivery_Id(deliveryId);
        List<com.thinkpalm.thinkfood.entity.DeliveryDetails> listDeliveryDetailsEntity=new ArrayList<>();
        deliveryDetailsEntityList.forEach(delivery->{
            if (("DELIVERED".equals(delivery.getDeliveryStatus()))) {
                log.info("Delivery Status: {}", delivery.getDeliveryStatus());
                log.info("IsActive: {}", delivery.getDeliveryId().getIsActive());
                log.info("Delivery ID: {}", delivery.getDeliveryId().getId());
                log.info("Delivery is equal to given id: {}",delivery.getDeliveryId().getId()==deliveryId);
                if (delivery.getDeliveryId().getIsActive()) {
                    if (delivery.getDeliveryId().getId() == deliveryId) {
                        log.info("To create entity for each delivery");
                        com.thinkpalm.thinkfood.entity.DeliveryDetails deliveryDetailsEntity = new com.thinkpalm.thinkfood.entity.DeliveryDetails();
                        log.info("Created entity for each delivery");
                        deliveryDetailsEntity.setDeliveryId(delivery.getDeliveryId());
                        deliveryDetailsEntity.setDeliveryStatus(delivery.getDeliveryStatus());
                        deliveryDetailsEntity.setCustomerId(delivery.getCustomerId());
                        deliveryDetailsEntity.setTimeTaken(delivery.getTimeTaken());
                        deliveryDetailsEntity.setOrderId(delivery.getOrderId());
                        deliveryDetailsEntity.setTotalDistance(delivery.getTotalDistance());
                        deliveryDetailsEntity.setRestaurantId(delivery.getRestaurantId());
                        deliveryDetailsEntity.setId(delivery.getId());
                        deliveryDetailsEntity.setCreatedDateTime(delivery.getCreatedDateTime());
                        listDeliveryDetailsEntity.add(deliveryDetailsEntity);
                    }
                }
            }
        });
        List<DeliveryDetailsDisplay> deliveryDetailsList=new ArrayList<>();
        if(!listDeliveryDetailsEntity.isEmpty()) {
            listDeliveryDetailsEntity.forEach(deliveryDetailsEntity -> {
                DeliveryDetailsDisplay deliveryDetails=new DeliveryDetailsDisplay();
                deliveryDetails.setId(deliveryDetailsEntity.getId());
                deliveryDetails.setRestaurantName(deliveryDetailsEntity.getRestaurantId().getRestaurantName());
                deliveryDetails.setDeliveryName(deliveryDetailsEntity.getDeliveryId().getDeliveryName());
                deliveryDetails.setCustomerName(deliveryDetailsEntity.getCustomerId().getCustomerName());
                deliveryDetails.setEndAddress(deliveryDetailsEntity.getCustomerId().getAddress());
                deliveryDetails.setOrderId(deliveryDetailsEntity.getOrderId().getId());
                deliveryDetails.setCreatedDateTime(deliveryDetailsEntity.getCreatedDateTime());
                deliveryDetails.setTimeTaken(deliveryDetailsEntity.getTimeTaken());
                deliveryDetails.setTotalDistance(deliveryDetailsEntity.getTotalDistance());
                deliveryDetails.setDeliveryStatus(deliveryDetailsEntity.getDeliveryStatus());
                Order OrderById = new Order();
                try {
                    List<DeliveryItem> deliveryItems = new ArrayList<>();
                    OrderById = orderService.findOrderById(deliveryDetailsEntity.getOrderId().getId());
                    OrderById.getOrderedItems().forEach(orderedItem -> {
                        DeliveryItem item = new DeliveryItem();
                        item.setItemName(orderedItem.getName());
                        item.setQuantity(orderedItem.getQuantity());
                        deliveryItems.add(item);
                    });
                    deliveryDetails.setDeliveryItems(deliveryItems);
                } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                }

                deliveryDetailsList.add(deliveryDetails);
            });
        }
        else {
            throw new DetailsNotFoundException("Details are not found");
        }

        log.info("Displayed delivery details.");
        return deliveryDetailsList;
    }

    /**
     * To display total number of orders delivered
     * @param deliveryId is delivery if of agent
     * @return total count
     */

    public int orderCount(Long deliveryId)
    {
        int count;
        log.info("Display total Number of orders ");
        List<com.thinkpalm.thinkfood.entity.DeliveryDetails> deliveryDetailsEntityList=deliveryDetailsRepository.findAllByDelivery_Id(deliveryId);
        count=deliveryDetailsEntityList.size();
        return count;
    }

    /**
          * This function to update the status of availability of one delivery agent and delivery.
          * If the agent is available,this function will update it to not available automatically and vice versa.
          * @return a string that update is successfully.
     */
    @Override
    public Boolean updateDeliveryDetailsByName(Long deliveryId)
    {
        log.info("Updated the status of order delivery details ");
        List<com.thinkpalm.thinkfood.entity.DeliveryDetails> deliveryDetails =deliveryDetailsRepository.findAllByDelivery_Id(deliveryId);
        if(!deliveryDetails.isEmpty()) {
            deliveryDetails.forEach(delivery ->
            {
                log.info("Checking if status is out for delivery.");
                if (String.valueOf(PreparationStatus.OUT_FOR_DELIVERY).equals(delivery.getDeliveryStatus())) {
                    log.info("It is out for delivery");
                    delivery.setDeliveryStatus(String.valueOf(PreparationStatus.ON_THE_WAY));
//                    Optional<com.thinkpalm.thinkfood.entity.Order> orderEntity=orderRepository.findById(delivery.getOrderId().getId());
//                    if(orderEntity.isPresent())
//                    {
//                        orderEntity.get().setPreparationStatus(String.valueOf(PreparationStatus.ON_THE_WAY));
//                    }
                    deliveryDetailsRepository.save(delivery);
//                    orderRepository.save(orderEntity.get());
                }
                else if(String.valueOf(PreparationStatus.ON_THE_WAY).equals(delivery.getDeliveryStatus()))
                {
                    log.info("It is on the way");
                    delivery.setDeliveryStatus(String.valueOf(PreparationStatus.DELIVERED));
                    Optional<com.thinkpalm.thinkfood.entity.Delivery> deliveryAgents = deliveryRepository.findById(delivery.getDeliveryId().getId());
                    if (deliveryAgents.isPresent()) {
                        deliveryAgents.get().setDeliveryAvailability("OPEN");
                    }
//                    Optional<com.thinkpalm.thinkfood.entity.Order> orderEntity=orderRepository.findById(delivery.getOrderId().getId());
//                    if(orderEntity.isPresent())
//                    {
//                        orderEntity.get().setPreparationStatus(String.valueOf(PreparationStatus.DELIVERED));
//                    }
                    deliveryDetailsRepository.save(delivery);
//                    orderRepository.save(orderEntity.get());
                }
            });
        }
        else {
            throw new DetailsNotFoundException("Delivery details are not found.");
        }
        return true;
    }

    @Override
    public Boolean updateRestaurantPreparationStatus(Long orderId)
    {
        Optional<com.thinkpalm.thinkfood.entity.Order> orderEntity=orderRepository.findById(orderId);
        if(orderEntity.isPresent())
        {

            Optional<com.thinkpalm.thinkfood.entity.DeliveryDetails> deliveryDetailsEntity=deliveryDetailsRepository.findAllByOrder_Id(orderId);
            if(deliveryDetailsEntity.isPresent())
            {
                log.info("Checking if status is preparing.");
                if("PREPARATION".equals(deliveryDetailsEntity.get().getDeliveryStatus())) {
                    if(deliveryDetailsEntity.get().getDeliveryStatus().equals(orderEntity.get().getPreparationStatus())){
                        log.info("The status are same-{}",deliveryDetailsEntity.get().getDeliveryStatus());
                        return false;
                    }
                    log.info("The status have changed from {} to {}" , deliveryDetailsEntity.get().getDeliveryStatus(),orderEntity.get().getPreparationStatus());
                    deliveryDetailsEntity.get().setDeliveryStatus(orderEntity.get().getPreparationStatus());
                    deliveryDetailsRepository.save(deliveryDetailsEntity.get());
                }
                else
                {
                    log.info("Status is not preparing");
                    log.info("The status have changed from {} to {}" ,orderEntity.get().getPreparationStatus(), deliveryDetailsEntity.get().getDeliveryStatus());
                    orderEntity.get().setPreparationStatus(deliveryDetailsEntity.get().getDeliveryStatus());
                    orderRepository.save(orderEntity.get());
                    deliveryDetailsRepository.save(deliveryDetailsEntity.get());
                }
            }
            else {
                throw new DetailsNotFoundException(" Delivery Details are not found");
            }
        }
        else {
            throw new DetailsNotFoundException("Order Details are not found");
        }
        return true;
    }

}


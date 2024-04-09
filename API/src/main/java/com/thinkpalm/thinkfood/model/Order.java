package com.thinkpalm.thinkfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkpalm.thinkfood.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Represents an Order in the system.
 * @version 2.0
 * @since 31/10/23
 * @author daan.j
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {


    /**
     * The unique identifier for the order.
     */
    Long id;
   // private Long customerId;

    /**
     * The name of the customer associated with the order.
     */
    private String customerName;

    /**
     * The unique identifier for the cart.
     */
    private Long cartId;
   // private Long couponId;

    /**
     * The coupon code applied to the order.
     */
    private String couponCode;

    /**
     * The discount applied through the coupon.
     */
    private Double couponDiscount;

    /**
     * The total cost of the order.
     */
    private Double totalCost;

    /**
     * The address associated with the order.
     */
    private String address;

    /**
     * The GST amount for the order.
     */
    private Double GstAmount;

    /**
     * The list of cart items in the order.
     */
    @JsonIgnore
    private List<CartItem> cartItems;
   // private List<String> orderedItemNames;

    /**
     * The list of ordered items in the order.
     */
    private List<OrderedItem> orderedItems;


    /**
     * The preparation status of the order.
     */

    private String preparationStatus="PREPARATION";

    private String deliveryStatus;

    private Long restaurantId;

    private LocalDateTime date;

}

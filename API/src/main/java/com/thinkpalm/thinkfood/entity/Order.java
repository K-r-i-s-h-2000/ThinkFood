package com.thinkpalm.thinkfood.entity;

//import com.thinkpalm.thinkfood.entity.Cart;
import com.thinkpalm.thinkfood.entity.Category;
import com.thinkpalm.thinkfood.entity.EntityDoc;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * Entity class representing Order.
 * This class extends EntityDoc and represents an order in the system.
 *
 * @author daan.j
 * @version 2.0
 * @since 31/10/23
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends EntityDoc {


//    @Column(name = "cart_id")
//    private long cartId;

    /**
     * The ID of the coupon associated with the order.
     */
    @Column(name = "coupon_id")
    private long couponId;

    /**
     * The code of the coupon associated with the order.
     */
    @Column(name= "coupon_code")
    private String couponCode;

    /**
     * The discount amount applied by the coupon.
     */

    @Column(name = "coupon_discount")
    private double couponDiscount;

    /**
     * The total cost of the order.
     */

    @Column(name = "total_cost")
    private double totalCost;

    /**
     * The cart associated with the order.
     */

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id",insertable=false)
    private Cart cart;

    /**
     * The customer who placed the order.
     */

    @OneToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

    /**
     * The delivery status of the order.
     */

    @Column(name = "delivery_status", nullable=false, length=100)
    private String deliveryStatus="NOT DONE";

    /**
     * The preparation status of the order.
     */

    private String preparationStatus="PREPARATION";

    @Column(name="is_active")
    private Boolean isActive=false;

    @OneToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

}

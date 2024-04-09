package com.thinkpalm.thinkfood.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


/**
 * Entity class representing a Coupon.
 * @author daan.j
 * @version 2.0
 * @since 31/10/23
 */
@Entity
@Table(name = "coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Coupon extends EntityDoc {


    /**
     * The code associated with the coupon.
     */

    @Column(name = "coupon_code")
    private String couponCode;

    /**
     * The discount percentage offered by the coupon.
     */
    @Column(name = "discount_percentage")
    private double discountPercentage;

    @Column(name="is_active")
    private Boolean isActive=false;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

}

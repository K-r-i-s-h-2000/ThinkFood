package com.thinkpalm.thinkfood.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Model class representing a Coupon.
 * This class contains information about a coupon, including its unique identifier, code, and discount percentage.
 *
 * @version 2.0
 * @since 31/10/23
 * @author daan.j
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    /**
     * The unique identifier of the coupon.
     */
    private Long id;

    /**
     * The code associated with the coupon.
     */
    private String couponCode;

    /**
     * The percentage of discount offered by the coupon.
     */
    private Double discountPercentage;

    private LocalDate expiryDate;
}
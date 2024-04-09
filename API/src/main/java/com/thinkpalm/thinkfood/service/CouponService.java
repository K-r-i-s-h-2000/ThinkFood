package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.exception.CouponCodeAlreadyExistsException;
import com.thinkpalm.thinkfood.exception.NotFoundException;

import java.util.List;
/**
 * Service interface for managing coupons.
 * This interface defines methods for adding, updating, and deleting coupons.
 * It also specifies an exception that may be thrown in case of errors.
 * author: daan.j
 * version: 2.0
 * since: 31/10/23
 */
public interface CouponService {
    List<com.thinkpalm.thinkfood.model.Coupon> getAllCoupons();

    /**
     * Adds a new coupon.
     *
     * @param coupon The coupon to be added.
     * @return The added coupon.
     * @throws CouponCodeAlreadyExistsException If the coupon code already exists.
     */
    com.thinkpalm.thinkfood.model.Coupon addCoupon(com.thinkpalm.thinkfood.model.Coupon coupon) throws CouponCodeAlreadyExistsException;

    /**
     * Updates an existing coupon.
     *
     * @param coupon The code of the coupon to be updated.
     * @param updatedCoupon The updated coupon details.
     * @return The updated coupon.
     * @throws NotFoundException If the coupon cannot be found.
     */
    com.thinkpalm.thinkfood.model.Coupon updateCoupon(String coupon, com.thinkpalm.thinkfood.model.Coupon updatedCoupon) throws NotFoundException;


    //com.thinkpalm.thinkfood.model.Coupon deleteCoupon(Long id) throws NotFoundException;

    /**
     * Deletes a coupon by its code.
     *
     * @param coupon The code of the coupon to be deleted.
     * @return The deleted coupon.
     * @throws NotFoundException If the coupon cannot be found.
     */
    com.thinkpalm.thinkfood.model.Coupon deleteCoupon(String coupon)throws NotFoundException;

    String softDeleteCouponById(Long id) throws NotFoundException;

     com.thinkpalm.thinkfood.model.Coupon getCouponByCouponCode(String couponCode) throws NotFoundException, CouponCodeAlreadyExistsException;

    public List<com.thinkpalm.thinkfood.model.Coupon> getAllCouponsWithExpiry();


}


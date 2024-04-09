package com.thinkpalm.thinkfood.service;

import com.thinkpalm.thinkfood.entity.Coupon;
import com.thinkpalm.thinkfood.exception.CouponCodeAlreadyExistsException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.repository.CouponRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing coupons.
 * This class provides methods for adding, updating, and deleting coupons.
 * It interacts with the repository to perform database operations.
 * author: daan.j
 * version: 2.0
 * since: 31/10/23
 */
@Service
public class CouponServiceImplementation implements CouponService{

    @Autowired
    CouponRepository couponRepository;



    /**
     * Adds a new coupon.
     *
     * @param coupon The coupon to be added.
     * @return The added coupon.
     * @throws CouponCodeAlreadyExistsException If the coupon code already exists.
     */
    @Override
    public com.thinkpalm.thinkfood.model.Coupon addCoupon(com.thinkpalm.thinkfood.model.Coupon coupon) throws CouponCodeAlreadyExistsException {

        Optional<Coupon> coupon1 = couponRepository.findByCouponCode((coupon.getCouponCode()));
        if (coupon1.isPresent() && coupon1.get().getIsActive()) {
            throw new CouponCodeAlreadyExistsException("Coupon is  already present");
        }

        if(coupon.getDiscountPercentage() > 100)
        {
            throw new NotFoundException("Exceeded 100% value");
        }


        Coupon couponEntity = new Coupon();
        couponEntity.setCouponCode(coupon.getCouponCode());
        couponEntity.setDiscountPercentage(coupon.getDiscountPercentage());
        couponEntity.setExpiryDate(coupon.getExpiryDate());
        couponEntity.setIsActive(true);
        couponEntity = couponRepository.save(couponEntity);

        com.thinkpalm.thinkfood.model.Coupon couponModel = new com.thinkpalm.thinkfood.model.Coupon();

        couponModel.setId(couponEntity.getId());
        couponModel.setCouponCode(coupon.getCouponCode());
        couponModel.setDiscountPercentage(coupon.getDiscountPercentage());
        couponModel.setExpiryDate(coupon.getExpiryDate());

        return couponModel;

    }


    /**
     * Updates an existing coupon.
     *
     * @param coupon The code of the coupon to be updated.
     * @param updatedCoupon The updated coupon details.
     * @return The updated coupon.
     * @throws NotFoundException If the coupon cannot be found.
     */



    @Override
    public com.thinkpalm.thinkfood.model.Coupon updateCoupon(String coupon, com.thinkpalm.thinkfood.model.Coupon updatedCoupon) throws NotFoundException {
         Coupon couponEntity = couponRepository.findByCouponCode(coupon)
                .orElseThrow(() -> new NotFoundException("No such coupon Found" + coupon ));
        if (!couponEntity.getIsActive()) {
            throw new NotFoundException("Invalid coupon id");
        }

         if(updatedCoupon.getCouponCode() != null && !updatedCoupon.getCouponCode().isEmpty())
         {
             couponEntity.setCouponCode(updatedCoupon.getCouponCode());
         }
        if(updatedCoupon.getDiscountPercentage() != null )
        {
            couponEntity.setDiscountPercentage(updatedCoupon.getDiscountPercentage());
        }
        if(updatedCoupon.getDiscountPercentage() > 100)
        {
            throw new NotFoundException("Exceeded 100% value");
        }

        couponRepository.save(couponEntity);

        com.thinkpalm.thinkfood.model.Coupon couponModel = new com.thinkpalm.thinkfood.model.Coupon();
        couponModel.setCouponCode(couponEntity.getCouponCode());
        couponModel.setDiscountPercentage(couponEntity.getDiscountPercentage());

        return couponModel;


    }

//    @Override
//    public com.thinkpalm.thinkfood.model.Coupon deleteCoupon(Long id) throws NotFoundException {
//        Optional<Coupon> couponOptional = couponRepository.findById(id);
//
//        if (couponOptional.isEmpty()) {
//            throw new NotFoundException("No such coupon");
//        }
//
//        Coupon deletedCoupon = couponOptional.get();
//
//        com.thinkpalm.thinkfood.model.Coupon deletedCouponModel = new com.thinkpalm.thinkfood.model.Coupon();
//        BeanUtils.copyProperties(deletedCoupon, deletedCouponModel);
//
//        couponRepository.deleteById(id);
//
//        return deletedCouponModel;
//    }

    /**
     * Deletes a coupon by its code.
     *
     * @param coupon The code of the coupon to be deleted.
     * @return The deleted coupon.
     * @throws NotFoundException If the coupon cannot be found.
     */

    @Override
    public com.thinkpalm.thinkfood.model.Coupon deleteCoupon(String coupon) throws NotFoundException {
        Optional<Coupon> couponOptional = couponRepository.findByCouponCode(coupon);
        Long idd = couponOptional.get().getId();
        if(couponOptional.isEmpty())
        {
            throw new NotFoundException("No such coupon");
        }

        Coupon deletedCoupon= couponOptional.get();


        com.thinkpalm.thinkfood.model.Coupon deletedCouponModel = new com.thinkpalm.thinkfood.model.Coupon();
        BeanUtils.copyProperties(deletedCoupon,deletedCouponModel);

        couponRepository.deleteById(idd);

        return deletedCouponModel;



    }

    @Override
    public String softDeleteCouponById(Long id) throws NotFoundException {

        com.thinkpalm.thinkfood.entity.Coupon coupon = couponRepository.findById(id).orElseThrow(() -> new NotFoundException("Coupon with ID " + id + " not found"));

        if (!coupon.getIsActive()) {
            throw new NotFoundException("Invalid order id");
        }
        coupon.setIsActive(false);

        couponRepository.save(coupon);

        return "Coupon With Id "+id+" deleted successfully!";
    }

    @Override
    public com.thinkpalm.thinkfood.model.Coupon getCouponByCouponCode(String couponCode) throws NotFoundException, CouponCodeAlreadyExistsException {
        Optional<Coupon> couponOptional = couponRepository.findByCouponCode(couponCode);
        if(isCouponExpired(couponCode))
        {
            throw new CouponCodeAlreadyExistsException("Coupon Expired");
        }
        if (couponOptional.isEmpty() || !couponOptional.get().getIsActive())
        {
            throw new NotFoundException("No such Coupon");
        }
        Coupon couponEntity=new Coupon();
        couponEntity=couponOptional.get();

        com.thinkpalm.thinkfood.model.Coupon couponModel = new com.thinkpalm.thinkfood.model.Coupon();
        couponModel.setId(couponEntity.getId());
        couponModel.setCouponCode(couponEntity.getCouponCode());
        couponModel.setDiscountPercentage(couponEntity.getDiscountPercentage());

        return couponModel;


    }



    @Override
    public List<com.thinkpalm.thinkfood.model.Coupon> getAllCouponsWithExpiry() {
        List<Coupon> coupons = couponRepository.findAll();

        List<com.thinkpalm.thinkfood.model.Coupon> couponModels = new ArrayList<>();
        for (Coupon coupon : coupons) {
            int i =0;
            if (coupon.getIsActive() == true && !isCouponExpired(coupon.getCouponCode()) && coupon.getDiscountPercentage()<50) {
                com.thinkpalm.thinkfood.model.Coupon couponModel = new com.thinkpalm.thinkfood.model.Coupon();
                couponModel.setId(coupon.getId());
                couponModel.setCouponCode(coupon.getCouponCode());
                couponModel.setDiscountPercentage(coupon.getDiscountPercentage());
                couponModel.setExpiryDate(coupon.getExpiryDate());

                couponModels.add(couponModel);
            }
            i++;
        }

        return couponModels;
    }

    @Override
    public List<com.thinkpalm.thinkfood.model.Coupon> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();

        List<com.thinkpalm.thinkfood.model.Coupon> couponModels = new ArrayList<>();
        for (Coupon coupon : coupons) {
            if (coupon.getIsActive() == true) {
                com.thinkpalm.thinkfood.model.Coupon couponModel = new com.thinkpalm.thinkfood.model.Coupon();
                couponModel.setId(coupon.getId());
                couponModel.setCouponCode(coupon.getCouponCode());
                couponModel.setDiscountPercentage(coupon.getDiscountPercentage());
                couponModel.setExpiryDate(coupon.getExpiryDate());
                couponModels.add(couponModel);
            }
        }

        return couponModels;
    }

    public boolean isCouponExpired(String couponCode) {
        Optional<Coupon> couponOptional = couponRepository.findByCouponCode(couponCode);

        if (couponOptional.isPresent()) {
            Coupon coupon = couponOptional.get();

            LocalDate expiryDate = coupon.getExpiryDate();

            LocalDate currentDate = LocalDate.now();

            return currentDate.isAfter(expiryDate);
        } else {
            return false;

        }
    }
}

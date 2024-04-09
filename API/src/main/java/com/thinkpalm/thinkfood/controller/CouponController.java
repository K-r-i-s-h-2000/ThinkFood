package com.thinkpalm.thinkfood.controller;

import com.thinkpalm.thinkfood.exception.CouponCodeAlreadyExistsException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.model.Coupon;
import com.thinkpalm.thinkfood.service.CouponService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling the coupons.
 * This class is responsible for receiving and sending requests related to coupons and
 * interacts with the corresponding service implementation to perform operations.
 *
 * @version 2.0
 * @since 31/10/23
 * @author daan.j
 */

@Log4j2
@RestController
@RequestMapping("/think-food/coupons")
@SecurityRequirement(name="thinkfood")
public class CouponController {

    private static final Logger logger = LogManager.getLogger(CouponController.class);
    @Autowired
    private CouponService couponService;

    @GetMapping("/get-all-coupon")
    public List<Coupon> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    @GetMapping("/get-all-coupon-with-expiry")
    public List<Coupon> getAllCouponsWithExpiry() {
        return couponService.getAllCouponsWithExpiry();
    }

//    @GetMapping("/{id}")
//    public Coupon getCouponById(@PathVariable Long id) {
//        return couponService.getCouponById(id);
//    }

//    @GetMapping("/code/{code}")
//    public Coupon getCouponByCode(@PathVariable String code) {
//        return couponService.getCouponByCode(code);
//    }

    /**
     * Add a new coupon.
     *
     * @param coupon The details of the coupon to be added.
     * @return ResponseEntity<Object> The added coupon with HTTP status.
     */

    @PostMapping
    public ResponseEntity<Object> addCoupon(@RequestBody com.thinkpalm.thinkfood.model.Coupon coupon) {
        try {
            logger.info("Entered into addCoupon Controller");
            com.thinkpalm.thinkfood.model.Coupon addedCoupon = couponService.addCoupon(coupon);
            return ResponseEntity.ok(addedCoupon);
        } catch (CouponCodeAlreadyExistsException e) {
            logger.error("Entered coupon code already exist in Add Coupon Controller");
            return ResponseEntity.status(HttpStatus.OK).body("Coupon Code already exist");
        }
        catch (NotFoundException e){
            logger.error("Entered discount percentage exceeds 100");
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }

    /**
     * Update an existing coupon.
     *
     * @param coupon The code of the coupon to be updated.
     * @param updatedCoupon The updated coupon details.
     * @return ResponseEntity<com.thinkpalm.thinkfood.model.Coupon> The updated coupon with HTTP status.
     */
    @PutMapping("/update/{coupon}")
    public ResponseEntity<com.thinkpalm.thinkfood.model.Coupon> updateItem(@PathVariable String coupon, @RequestBody com.thinkpalm.thinkfood.model.Coupon updatedCoupon) {
        try {
            logger.info("Entered into Update Item Controller");
            com.thinkpalm.thinkfood.model.Coupon updated = couponService.updateCoupon(coupon,updatedCoupon);
            return ResponseEntity.ok(updated);
        } catch (NotFoundException e) {
            logger.error("No such coupon present to update");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a coupon by its code.
     *
     * @param coupon The code of the coupon to be deleted.
     * @return ResponseEntity<String> A message indicating the result of the deletion.
     * @throws NotFoundException If the coupon cannot be found.
     */

    @DeleteMapping("/hard-delete/{coupon}")
    public ResponseEntity<String> deleteItemById(@PathVariable String coupon) throws NotFoundException {
        logger.info("Entered into Delete Item By Id Controller");
        couponService.deleteCoupon(coupon);
        return ResponseEntity.ok("Coupon :" + coupon + " has been deleted.");
    }

    @DeleteMapping("/delete/{id}")
    public Boolean softDeleteCoupon(@PathVariable Long id) {
        try {
            String response = couponService.softDeleteCouponById(id);
            logger.info("Delete for Order with ID " + id + " was successful.");
            return true;
        } catch (NotFoundException e) {
            logger.error(" Order not found for ID " + id + ": " + e.getMessage());
            return false;
        }
    }

    @GetMapping("/{couponCode}")
    public ResponseEntity<?> getCouponByCouponCode(@PathVariable String couponCode) {
        try {
            Coupon coupon = couponService.getCouponByCouponCode(couponCode);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CouponCodeAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//
//    @PutMapping("/update")
//    public ResponseEntity<Object> updateCoupon(
//            @RequestParam String couponCode,
//            @RequestBody com.thinkpalm.thinkfood.model.Coupon updatedCoupon
//    ) {
//        try {
//            com.thinkpalm.thinkfood.model.Coupon updatedCouponModel = couponService.updateCoupon(couponCode, updatedCoupon);
//            return ResponseEntity.ok(updatedCouponModel);
//        } catch (NotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such coupon");
//        }
//    }


//    @PutMapping("/{id}")
//    public void updateCoupon(@PathVariable String coupon, @RequestBody Double discount) throws NotFoundException {
//        couponService.updateCoupon(coupon,discount);
//    }





//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteItemById(@PathVariable Long id) throws NotFoundException {
//        couponService.deleteCoupon(id);
//        return ResponseEntity.ok("Item with ID " + id + " has been deleted.");
//    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<Object> deleteCoupon(@RequestParam String coupon) {
//        try {
//            couponService.deleteCoupon(id);
//            return ResponseEntity.ok("Coupon deleted successfully");
//        } catch (NotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
//        }
//    }



//
//    @DeleteMapping("/delete/{coupon}")
//    public String deleteCoupon(@PathVariable String coupon) throws NotFoundException {
//        couponService.deleteCoupon(coupon);
//        return "deleted";
//    }
}


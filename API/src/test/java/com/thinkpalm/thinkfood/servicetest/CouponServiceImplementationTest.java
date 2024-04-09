package com.thinkpalm.thinkfood.servicetest;


import com.thinkpalm.thinkfood.entity.Coupon;
import com.thinkpalm.thinkfood.exception.CouponCodeAlreadyExistsException;
import com.thinkpalm.thinkfood.exception.NotFoundException;
import com.thinkpalm.thinkfood.repository.CouponRepository;
import com.thinkpalm.thinkfood.service.CouponService;
import com.thinkpalm.thinkfood.service.CouponServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CouponServiceImplementationTest {

    @Mock
    CouponRepository couponRepository;

    @InjectMocks
    CouponServiceImplementation couponService;

    @Test
    void addCoupon() throws CouponCodeAlreadyExistsException {
        com.thinkpalm.thinkfood.model.Coupon couponModel = new com.thinkpalm.thinkfood.model.Coupon();
        couponModel.setCouponCode("FLAT50");
        couponModel.setDiscountPercentage(3.2);

        when(couponRepository.findByCouponCode("FLAT50")).thenReturn(Optional.empty());

        Coupon couponEntity = new Coupon();
        couponEntity.setId(1L);
        couponEntity.setCouponCode("FLAT50");
        couponEntity.setDiscountPercentage(3.2);

        when(couponRepository.save(any(Coupon.class))).thenReturn(couponEntity);

        com.thinkpalm.thinkfood.model.Coupon result = couponService.addCoupon(couponModel);

        assertEquals(couponEntity.getId(), result.getId());
        assertEquals(couponEntity.getCouponCode(), result.getCouponCode());
        assertEquals(couponEntity.getDiscountPercentage(), result.getDiscountPercentage());
    }

    @Test
    void updateCoupon() throws NotFoundException {
        String couponCode = "FLAT22";
        com.thinkpalm.thinkfood.model.Coupon updatedCouponModel = new com.thinkpalm.thinkfood.model.Coupon();
        updatedCouponModel.setCouponCode("FLAT50");
        updatedCouponModel.setDiscountPercentage(4.2);


        Coupon existingCouponEntity = new Coupon();
        existingCouponEntity.setId(8L);
        existingCouponEntity.setCouponCode("FLAT50");
        existingCouponEntity.setDiscountPercentage(4.2);
        existingCouponEntity.setIsActive(Boolean.TRUE);

        when(couponRepository.findByCouponCode(couponCode)).thenReturn(Optional.of(existingCouponEntity));

        com.thinkpalm.thinkfood.model.Coupon result = couponService.updateCoupon(couponCode, updatedCouponModel);

        assertEquals(updatedCouponModel.getCouponCode(), result.getCouponCode());
        assertEquals(updatedCouponModel.getDiscountPercentage(), result.getDiscountPercentage());
    }
    @Test
    void deleteCoupon() throws NotFoundException {
        String couponCode = "FLAT50";

        Coupon existingCouponEntity = new Coupon();
        existingCouponEntity.setId(1L);
        existingCouponEntity.setCouponCode("FLAT50");
        existingCouponEntity.setDiscountPercentage(3.2);

        when(couponRepository.findByCouponCode(couponCode)).thenReturn(Optional.of(existingCouponEntity));

        com.thinkpalm.thinkfood.model.Coupon result = couponService.deleteCoupon(couponCode);

        assertEquals(existingCouponEntity.getCouponCode(), result.getCouponCode());
        assertEquals(existingCouponEntity.getDiscountPercentage(), result.getDiscountPercentage());
    }

    @Test
    void softDeleteCouponById_validId_couponDeletedSuccessfully() throws NotFoundException {
        long couponId = 1L;

        Coupon coupon = new Coupon();
        coupon.setId(couponId);
        coupon.setIsActive(true);

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));

        String result = couponService.softDeleteCouponById(couponId);

        verify(couponRepository, times(1)).findById(couponId);
        verify(couponRepository, times(1)).save(coupon);

        assertFalse(coupon.getIsActive());
        assertEquals("Coupon With Id " + couponId + " deleted successfully!", result);
    }

    @Test
    void softDeleteCouponById_invalidId_shouldThrowNotFoundException() {
        long invalidCouponId = 99L;

        when(couponRepository.findById(invalidCouponId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            couponService.softDeleteCouponById(invalidCouponId);
        });
    }

    @Test
    void softDeleteCouponById_inactiveCoupon_shouldThrowNotFoundException() {
        long inactiveCouponId = 2L;

        Coupon inactiveCoupon = new Coupon();
        inactiveCoupon.setId(inactiveCouponId);
        inactiveCoupon.setIsActive(false);

        when(couponRepository.findById(inactiveCouponId)).thenReturn(Optional.of(inactiveCoupon));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            couponService.softDeleteCouponById(inactiveCouponId);
        });
    }



}


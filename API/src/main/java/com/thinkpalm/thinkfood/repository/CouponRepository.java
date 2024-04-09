package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
/**
 * Repository interface for Coupon entities.
 * This interface provides methods for performing CRUD operations on Coupon entities.
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /**
     * Retrieve a coupon by its unique coupon code.
     *
     * @param couponCode The code associated with the coupon.
     * @return An Optional containing the coupon, or empty if not found.
     */
    Optional<Coupon> findByCouponCode(String couponCode);

    /**
     * Retrieve a coupon by its unique identifier.
     *
     * @param id The unique identifier of the coupon.
     * @return An Optional containing the coupon, or empty if not found.
     */
    Optional<Coupon> findById(Long id);


}

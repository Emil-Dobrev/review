package it.schwarz.jobs.review.coupon.domain.usecase;

import it.schwarz.jobs.review.coupon.domain.entity.Coupon;
import it.schwarz.jobs.review.coupon.domain.entity.CouponApplication;

import java.util.List;
import java.util.Optional;

public interface CouponProvider {
    Coupon createCoupon(Coupon coupon);

    List<Coupon> findAll();

    Optional<Coupon> findById(String couponCode);

    void registerApplication(String couponCode);

    Optional<CouponApplication> getCouponApplications(String couponCode);
}

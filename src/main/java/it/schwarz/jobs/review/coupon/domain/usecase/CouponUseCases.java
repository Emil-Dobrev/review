package it.schwarz.jobs.review.coupon.domain.usecase;

import it.schwarz.jobs.review.coupon.domain.entity.*;

import java.util.List;
import java.util.Optional;

public class CouponUseCases {

    private final CouponProvider couponProvider;

    public CouponUseCases(CouponProvider couponProvider) {
        this.couponProvider = couponProvider;
    }

    public Coupon createCoupon(Coupon coupon) {
        try {
            return couponProvider.createCoupon(coupon);
        } catch (IllegalStateException ex) {
            throw new CouponAlreadyExistsException(ex.getMessage());
        }
    }

    public List<Coupon> findAllCoupons() {
        return couponProvider.findAll();
    }

    public CouponApplication getApplications(String couponCode) {
        var foundCoupon = couponProvider.getCouponApplications(couponCode);
        if (foundCoupon.isEmpty()) {
            throw new CouponCodeNotFoundException("Coupon-Code " + couponCode + " not found.");
        }
        return foundCoupon.get();
    }

    public ApplicationResult applyCoupon(Basket basket, String couponCode) {

        AmountOfMoney basketValue = basket.getValue();
        Optional<Coupon> foundCoupon = couponProvider.findById(couponCode);

        // No Coupon not found for Coupon Code
        if (foundCoupon.isEmpty()) {
            throw new CouponCodeNotFoundException("Coupon-Code " + couponCode + " not found.");
        }

        // Basket value must not be less than discount
        Coupon couponToApply = foundCoupon.get();
        if (basketValue.isLessThan(couponToApply.getDiscount())) {
            throw new BasketValueTooLowException(
                    "The basket value (" + basketValue.toBigDecimal() + ") must not be less than the discount (" + couponToApply.getDiscount().toBigDecimal() + ").");
        }

        // Basket value must not be less than Coupon's minimal Basket Value
        if (basketValue.isLessThan(couponToApply.getMinBasketValue())) {
            throw new BasketValueTooLowException(
                    "The basket value (" + basketValue.toBigDecimal() + ") must not be less than the min. allowed basket value (" + couponToApply.getMinBasketValue().toBigDecimal() + ").");
        }

        // Register the usage of this coupon
        couponProvider.registerApplication(couponToApply.getCode());

        // Apply
        return new ApplicationResult(basket, couponToApply);
    }

}

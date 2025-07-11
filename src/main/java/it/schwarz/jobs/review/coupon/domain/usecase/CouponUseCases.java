package it.schwarz.jobs.review.coupon.domain.usecase;

import it.schwarz.jobs.review.coupon.domain.entity.ApplicationResult;
import it.schwarz.jobs.review.coupon.domain.entity.Basket;
import it.schwarz.jobs.review.coupon.domain.entity.Coupon;
import it.schwarz.jobs.review.coupon.domain.entity.CouponApplications;

import java.util.List;

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
        //  Instead of fetching the entire application list just to get the count,
        // it's more efficient to use a custom query that directly returns the application count using COUNT(*) in the database.
        // pagination could be included
        return couponProvider.findAll();
    }

    public CouponApplications getApplications(String couponCode) {
        //Use a custom  query to directly retrieve only the required data: couponCode and applicationTimestamps.
        //This avoids loading full entities and improves performance.
        var foundCouponApplications = couponProvider.getCouponApplications(couponCode);
        if (foundCouponApplications.isEmpty()) {
            throw new CouponCodeNotFoundException("Coupon-Code " + couponCode + " not found.");
        }
        return foundCouponApplications.get();
    }

    public ApplicationResult applyCoupon(Basket basket, String couponCode) {

        var basketValue = basket.getValue();
        //We can simplify the logic for checking if a coupon exists by using orElseThrow() directly on the Optional.
        // This improves readability and eliminates manual isEmpty() checks.
        //     var couponToApply = couponProvider.findById(couponCode)
        //    .orElseThrow(() -> new CouponCodeNotFoundException("Coupon-Code " + couponCode + " not found."));
        var foundCoupon = couponProvider.findById(couponCode);

        // No Coupon found for given Coupon Code
        //The basket validation logic can be extracted into a separate validateBasketValue(basket, coupon) method.
        // This improves readability, makes applyCoupon() easier to follow, and promotes reuse if similar validations are needed elsewhere.
        if (foundCoupon.isEmpty()) {
            throw new CouponCodeNotFoundException("Coupon-Code " + couponCode + " not found.");
        }

        // Basket value must not be less than discount
        var couponToApply = foundCoupon.get();
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
        couponProvider.registerCouponApplication(couponToApply.getCode());

        // Apply
        return new ApplicationResult(basket, couponToApply);
    }

}

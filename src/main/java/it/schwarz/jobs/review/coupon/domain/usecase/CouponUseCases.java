package it.schwarz.jobs.review.coupon.domain.usecase;

import it.schwarz.jobs.review.coupon.domain.entity.AmountOfMoney;
import it.schwarz.jobs.review.coupon.domain.entity.Basket;
import it.schwarz.jobs.review.coupon.domain.entity.Coupon;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Optional;

public class CouponUseCases {

    private final CouponProvider couponProvider;

    public CouponUseCases(CouponProvider couponProvider) {
        this.couponProvider = couponProvider;
    }

    public Coupon createCoupon(Coupon coupon) {
        Coupon persistedCoupon = couponProvider.createCoupon(coupon);
        return persistedCoupon;
    }

    public List<Coupon> findAll() {
        return couponProvider.findAll();
    }

    public Basket applyCoupon(AmountOfMoney basketValue, String couponCode) {

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
        return new Basket(basketValue, couponToApply.getDiscount(), true);
    }

}

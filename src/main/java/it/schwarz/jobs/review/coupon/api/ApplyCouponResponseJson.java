package it.schwarz.jobs.review.coupon.api;

import it.schwarz.jobs.review.coupon.domain.entity.ApplicationResult;

import java.math.BigDecimal;

public record ApplyCouponResponseJson(

        BasketJson basket,
        BigDecimal appliedDiscount) {

    public static ApplyCouponResponseJson of(ApplicationResult applicationResult) {
        return new ApplyCouponResponseJson(new BasketJson(
                applicationResult.getBasket().getValue().toBigDecimal()),
                applicationResult.getAppliedCoupon().getDiscount().toBigDecimal()
        );
    }
}

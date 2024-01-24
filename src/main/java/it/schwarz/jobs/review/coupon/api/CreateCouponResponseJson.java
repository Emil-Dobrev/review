package it.schwarz.jobs.review.coupon.api;

import it.schwarz.jobs.review.coupon.domain.entity.Coupon;

public record CreateCouponResponseJson(CouponJson coupon) {

    public static CreateCouponResponseJson of(Coupon coupon) {
        return new CreateCouponResponseJson(
                new CouponJson(
                        coupon.getCode(),
                        coupon.getDiscount().toBigDecimal(),
                        coupon.getMinBasketValue().toBigDecimal(),
                        coupon.getDescription(),
                        coupon.getApplicationCount()));
    }
}

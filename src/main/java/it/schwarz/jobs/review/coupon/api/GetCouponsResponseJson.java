package it.schwarz.jobs.review.coupon.api;

import it.schwarz.jobs.review.coupon.domain.entity.Coupon;

import java.util.List;

public record GetCouponsResponseJson(List<CouponJson> coupons) {

    public static GetCouponsResponseJson of(List<Coupon> coupons) {
        return new GetCouponsResponseJson(
                coupons.stream()
                        .map(coupon -> new CouponJson(
                                coupon.getCode(),
                                coupon.getDiscount().toBigDecimal(),
                                coupon.getMinBasketValue().toBigDecimal(),
                                coupon.getDescription(),
                                coupon.getApplicationCount()))
                        .toList());
    }
}

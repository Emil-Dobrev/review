package it.schwarz.jobs.review.coupon.api;

import jakarta.validation.Valid;

public record CreateCouponRequestJson(
    @Valid
    CouponJson coupon) {
}

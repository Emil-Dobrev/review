package it.schwarz.jobs.review.coupon.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ApplyCouponRequestJson(
    @Min(0)
    BigDecimal basketValue,
    @NotBlank
    String couponCode) {
}

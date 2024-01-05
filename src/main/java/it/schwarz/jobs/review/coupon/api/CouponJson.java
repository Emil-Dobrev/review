package it.schwarz.jobs.review.coupon.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CouponJson(
        @NotBlank
        String code,

        @Min(0)
        @Max(10000)
        BigDecimal discount,

        @Min(0)
        @Max(10000)
        BigDecimal minBasketValue,

        long applicationCount) {
}

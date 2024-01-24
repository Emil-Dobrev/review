package it.schwarz.jobs.review.coupon.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CouponJson(
        @Size(min = 1, max = 20)
        String code,

        @Min(0)
        @Max(10000)
        BigDecimal discount,

        @Min(0)
        @Max(10000)
        BigDecimal minBasketValue,

        @Size(min = 1, max = 1000)
        String description,

        long applicationCount) {
}

package it.schwarz.jobs.review.coupon.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ApplyCouponRequestJson(
        @Valid
        BasketJson basket,

        @NotBlank
        @Size(min = 1, max = 20)
        String couponCode
) {
}

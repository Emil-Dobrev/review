package it.schwarz.jobs.review.coupon.api;

import java.math.BigDecimal;

public record BasketJson(

    BigDecimal value,
    BigDecimal appliedDiscount,
    boolean applicationSuccessful) {
}

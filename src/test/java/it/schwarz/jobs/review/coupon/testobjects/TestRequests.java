package it.schwarz.jobs.review.coupon.testobjects;

import it.schwarz.jobs.review.coupon.api.ApplyCouponRequestJson;
import it.schwarz.jobs.review.coupon.api.BasketJson;
import it.schwarz.jobs.review.coupon.api.CreateCouponRequestJson;

import java.math.BigDecimal;

public class TestRequests {

    public CreateCouponRequestJson validCoupon() {
        return new CreateCouponRequestJson("CODE_12_20", new BigDecimal("12.00"), new BigDecimal("20.00"), "12 for 20");
    }

    public CreateCouponRequestJson invalidCouponOfNegativeDiscount() {
        return new CreateCouponRequestJson("CODE_12_20", new BigDecimal("-12.00"), new BigDecimal("20.00"), "12 for 20");
    }

    public ApplyCouponRequestJson validApplication() {
        return new ApplyCouponRequestJson(new BasketJson(new BigDecimal("60.00")), "TEST_05_50");
    }

    public ApplyCouponRequestJson invalidApplicationOfNotExistingCode() {
        return new ApplyCouponRequestJson(new BasketJson(new BigDecimal("60.00")), "<NOT-EXISTING-CODE>");
    }
}

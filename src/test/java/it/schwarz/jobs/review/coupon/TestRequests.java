package it.schwarz.jobs.review.coupon;

import it.schwarz.jobs.review.coupon.api.ApplyCouponRequestJson;
import it.schwarz.jobs.review.coupon.api.CouponJson;
import it.schwarz.jobs.review.coupon.api.CreateCouponRequestJson;

import java.math.BigDecimal;

public class TestRequests {

    public CreateCouponRequestJson validCoupon() {
        return new CreateCouponRequestJson(
                new CouponJson("CODE_12_20", new BigDecimal("12.00"), new BigDecimal("20.00"), 0)
        );
    }

    public CreateCouponRequestJson invalidCouponOfNegativeDiscount() {
        return new CreateCouponRequestJson(
                new CouponJson("CODE_12_20", new BigDecimal("-12.00"), new BigDecimal("20.00"), 0)
        );
    }

    public ApplyCouponRequestJson validApplication() {
        return new ApplyCouponRequestJson(new BigDecimal("60.00"), "TEST_10_50");
    }

    public ApplyCouponRequestJson invalidApplicationOfNotExistingCode() {
        return new ApplyCouponRequestJson(new BigDecimal("60.00"), "<NOT-EXISTING-CODE>");
    }
}

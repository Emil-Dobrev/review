package it.schwarz.jobs.review.coupon.domain.entity;

import java.time.Instant;
import java.util.List;

public class CouponApplication {
    private String couponCode;
    private List<Instant> applicationTimestamps;

    public CouponApplication(String couponCode, List<Instant> applicationDateTime) {
        this.couponCode = couponCode;
        this.applicationTimestamps = applicationDateTime;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public List<Instant> getApplicationTimestamps() {
        return applicationTimestamps;
    }
}

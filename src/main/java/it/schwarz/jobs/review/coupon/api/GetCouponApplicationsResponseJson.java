package it.schwarz.jobs.review.coupon.api;

import it.schwarz.jobs.review.coupon.domain.entity.CouponApplication;

import java.time.Instant;
import java.util.List;

public record GetCouponApplicationsResponseJson(
        String couponCode,
        List<Instant> applicationTimestamps
) {
    public static GetCouponApplicationsResponseJson of(CouponApplication couponApplication) {
        return new GetCouponApplicationsResponseJson(
                couponApplication.getCouponCode(),
                couponApplication.getApplicationTimestamps());
    }
}

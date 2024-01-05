package it.schwarz.jobs.review.coupon.api;

import java.util.List;

public record GetCouponsResponseJson(List<CouponJson> coupons) {
}

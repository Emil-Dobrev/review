package it.schwarz.jobs.review.coupon;

import it.schwarz.jobs.review.coupon.domain.entity.AmountOfMoney;
import it.schwarz.jobs.review.coupon.domain.entity.Coupon;

public class TestCoupons {

    public Coupon COUPON_12_20() {
        return new Coupon("CODE_12_20", AmountOfMoney.of("12.00"), AmountOfMoney.of("20.00"));
    }
}

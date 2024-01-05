package it.schwarz.jobs.review.coupon.provider.inmem;

import it.schwarz.jobs.review.coupon.domain.entity.AmountOfMoney;
import it.schwarz.jobs.review.coupon.domain.entity.Coupon;
import it.schwarz.jobs.review.coupon.domain.usecase.CouponProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class InMemoryCouponProvider implements CouponProvider {

    private final Set<Coupon> coupons = new HashSet<>();

    public InMemoryCouponProvider() {
        coupons.add(new Coupon("TEST_10_50", AmountOfMoney.of("10.00"), AmountOfMoney.of("50.00")));
        coupons.add(new Coupon("TEST_15_100", AmountOfMoney.of("15.00"), AmountOfMoney.of("100.00")));
        coupons.add(new Coupon("TEST_20_200", AmountOfMoney.of("20.00"), AmountOfMoney.of("200.00")));
    }



    @Override
    public Coupon createCoupon(Coupon coupon) {
        coupons.add(coupon);
        return coupon;
    }

    @Override
    public List<Coupon> findAll() {
        return coupons.stream().toList();
    }

    @Override
    public Optional<Coupon> findById(String couponCode) {
        return coupons.stream()
                .filter(it -> it.getCode().equals(couponCode))
                .findFirst();
    }

    @Override
    public void registerApplication(String couponCode) {
        // Intentionally left blank
    }
}

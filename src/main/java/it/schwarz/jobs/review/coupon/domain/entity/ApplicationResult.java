package it.schwarz.jobs.review.coupon.domain.entity;

public class ApplicationResult {
    private Basket basket;
    private Coupon appliedCoupon;

    public ApplicationResult(Basket basket, Coupon appliedCoupon) {
        this.basket = basket;
        this.appliedCoupon = appliedCoupon;
    }

    public Basket getBasket() {
        return basket;
    }

    public Coupon getAppliedCoupon() {
        return appliedCoupon;
    }
}

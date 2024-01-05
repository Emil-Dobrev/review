package it.schwarz.jobs.review.coupon.domain.entity;

public class Basket {
        private final AmountOfMoney value;
        private final AmountOfMoney appliedDiscount;
        private final boolean applicationSuccessful;

    public Basket(AmountOfMoney value, AmountOfMoney appliedDiscount, boolean applicationSuccessful) {
        this.value = value;
        this.appliedDiscount = appliedDiscount;
        this.applicationSuccessful = applicationSuccessful;
    }

    public AmountOfMoney getValue() {
        return value;
    }

    public AmountOfMoney getAppliedDiscount() {
        return appliedDiscount;
    }

    public boolean isApplicationSuccessful() {
        return applicationSuccessful;
    }
}

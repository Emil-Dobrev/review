package it.schwarz.jobs.review.coupon.domain.entity;

public class Coupon {

    private final String code;

    private final AmountOfMoney discount;

    private final AmountOfMoney minBasketValue;

    private final long applicationCount;
    public Coupon(String code, AmountOfMoney discount, AmountOfMoney minBasketValue) {
        this(code, discount, minBasketValue, 0);
    }

    public Coupon(String code, AmountOfMoney discount, AmountOfMoney minBasketValue, long applicationCount) {
        this.code = code;
        this.discount = discount;
        this.minBasketValue = minBasketValue;
        this.applicationCount = applicationCount;
    }

    public String getCode() {
        return code;
    }

    public AmountOfMoney getDiscount() {
        return discount;
    }

    public AmountOfMoney getMinBasketValue() {
        return minBasketValue;
    }

    public long getApplicationCount() {
        return applicationCount;
    }


}

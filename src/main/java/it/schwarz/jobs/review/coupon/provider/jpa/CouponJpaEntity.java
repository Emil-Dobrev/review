package it.schwarz.jobs.review.coupon.provider.jpa;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="COUPON")
public class CouponJpaEntity {

    public CouponJpaEntity() {
    }

    public CouponJpaEntity(String code, BigDecimal discount, BigDecimal minBasketValue) {
        this.code = code;
        this.discount = discount;
        this.minBasketValue = minBasketValue;
    }

    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "DISCOUNT", precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "MIN_BASKET_VALUE", precision = 10, scale = 2)
    private BigDecimal minBasketValue;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @OneToMany(mappedBy = "code")
    private List<ApplicationJpaEntity> applications;

    public String getCode() {
        return code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getMinBasketValue() {
        return minBasketValue;
    }

    public String getDescription() {
        return description;
    }

    public List<ApplicationJpaEntity> getApplications() {
        return applications;
    }
}

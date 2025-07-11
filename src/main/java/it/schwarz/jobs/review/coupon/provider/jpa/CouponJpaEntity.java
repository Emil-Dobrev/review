package it.schwarz.jobs.review.coupon.provider.jpa;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "COUPON")
public class CouponJpaEntity {

    @Id
    @Column(name = "CODE", nullable = false)
    private String code;
    @Column(name = "DISCOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;
    @Column(name = "MIN_BASKET_VALUE", nullable = false, precision = 10, scale = 2)
    private BigDecimal minBasketValue;
    @Column(name = "DESCRIPTION", nullable = false, length = 1000)
    private String description;
    // --- CRITICAL IMPROVEMENT: @OneToMany mapping ---
    // mappedBy should refer to the FIELD NAME in ApplicationJpaEntity that OWNS the relationship.
    // This field should be of type CouponJpaEntity, not String.
    // CascadeType.ALL is often used for parent-child relationships where children's lifecycle
    // depends on the parent. OrphanRemoval ensures children are deleted if removed from the collection.
    // @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "couponCode")
    // Initialize to avoid NullPointerException on getApplications().size() or iteration
    // private List<ApplicationJpaEntity> applications = new ArrayList<>();
    private List<ApplicationJpaEntity> applications;

    public CouponJpaEntity() {
    }

    public CouponJpaEntity(String code, BigDecimal discount, String description, BigDecimal minBasketValue) {
        this.code = code;
        this.discount = discount;
        this.description = description;
        this.minBasketValue = minBasketValue;
    }

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

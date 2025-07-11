package it.schwarz.jobs.review.coupon.provider.jpa;


import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "APPLICATION")
public class ApplicationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    // use Long for id
    private long id;
    //Create a @ManyToOne relationship  using a CouponJpaEntity
    // @JoinColumn(name = "COUPON_CODE", nullable = false)
    @Column(name = "COUPON_CODE", nullable = false)
    private String couponCode;
    // Here we can use @CreationTimestamp. Automatically populates the timestamp on entity creation
    @Column(name = "TIMESTAMP", nullable = false)
    private Instant timestamp;


    public ApplicationJpaEntity() {
    }

    public ApplicationJpaEntity(String code, Instant timestamp) {
        this.couponCode = code;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}

package it.schwarz.jobs.review.coupon.provider.jpa;

import it.schwarz.jobs.review.coupon.domain.entity.AmountOfMoney;
import it.schwarz.jobs.review.coupon.domain.entity.Coupon;
import it.schwarz.jobs.review.coupon.domain.entity.CouponApplication;
import it.schwarz.jobs.review.coupon.domain.usecase.CouponProvider;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JpaCouponProvider implements CouponProvider {

    private final CouponJpaRepository couponJpaRepository;
    private final ApplicationRepository applicationRepository;

    public JpaCouponProvider(CouponJpaRepository couponJpaRepository, ApplicationRepository applicationRepository) {
        this.couponJpaRepository = couponJpaRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    @Transactional
    public Coupon createCoupon(Coupon coupon) {
        if (couponJpaRepository.existsById(coupon.getCode())) {
            throw new IllegalStateException("Coupon already exists: " + coupon.getCode());
        }
        CouponJpaEntity toPersist = domainToJpa(coupon);
        CouponJpaEntity persisted = couponJpaRepository.save(toPersist);
        return jpaToDomain(persisted);
    }

    @Override
    public List<Coupon> findAll() {
        return couponJpaRepository.findAll().stream()
                .map(this::jpaToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void registerApplication(String couponCode) {
        applicationRepository.save(new ApplicationJpaEntity(
                couponCode,
                Instant.now()));
    }

    @Override
    public Optional<Coupon> findById(String couponCode) {
        Optional<CouponJpaEntity> found = couponJpaRepository.findById(couponCode);
        return found.map(this::jpaToDomain);
    }

    @Override
    public Optional<CouponApplication> getCouponApplications(String couponCode) {
        Optional<CouponJpaEntity> found = couponJpaRepository.findById(couponCode);
        if (found.isPresent()) {
            return Optional.of(new CouponApplication(
                    found.get().getCode(),
                    found.get().getApplications().stream()
                            .map(applicationJpaEntity -> applicationJpaEntity.getTimestamp())
                            .toList()));
        }
        return Optional.empty();

    }

    private CouponJpaEntity domainToJpa(Coupon coupon) {
        return new CouponJpaEntity(
                coupon.getCode(),
                coupon.getDiscount().toBigDecimal(),
                coupon.getDescription(),
                coupon.getMinBasketValue().toBigDecimal()
        );
    }

    private Coupon jpaToDomain(CouponJpaEntity couponJpaEntity) {

        return new Coupon(
                couponJpaEntity.getCode(),
                AmountOfMoney.of(couponJpaEntity.getDiscount()),
                AmountOfMoney.of(couponJpaEntity.getMinBasketValue()),
                couponJpaEntity.getDescription(),
                couponJpaEntity.getApplications() == null ? 0 : couponJpaEntity.getApplications().size()
        );
    }

}

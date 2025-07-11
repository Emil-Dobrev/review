package it.schwarz.jobs.review.coupon.provider.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

// We should add here @Repository
public interface CouponJpaRepository extends JpaRepository<CouponJpaEntity, String> {

    // new method to avoid N+1 problem when loading coupons with applications
   //  @Query("SELECT DISTINCT c FROM CouponJpaEntity c LEFT JOIN FETCH c.applications")
   //  List<CouponJpaEntity> findAllWithApplications();
}

package it.schwarz.jobs.review.coupon.provider.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

// We should add here @Repository
public interface ApplicationJpaRepository extends JpaRepository<ApplicationJpaEntity, String> {


}

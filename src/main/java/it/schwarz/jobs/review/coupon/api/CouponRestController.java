package it.schwarz.jobs.review.coupon.api;

import it.schwarz.jobs.review.coupon.domain.entity.Coupon;
import it.schwarz.jobs.review.coupon.domain.usecase.CouponUseCases;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CouponRestController {

    private final CouponUseCases couponUseCases;


    public CouponRestController(CouponUseCases couponUseCases) {
        this.couponUseCases = couponUseCases;
    }


    @GetMapping("/api/coupons")
    public ResponseEntity<GetCouponsResponseJson> getCoupons() {
        List<Coupon> coupons = couponUseCases.findAllCoupons();

        // Map from Domain to API
        GetCouponsResponseJson response = GetCouponsResponseJson.of(coupons);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/coupons/{couponCode}/applications")
    public ResponseEntity<GetCouponApplicationsResponseJson> getCouponApplications(@PathVariable("couponCode") String couponCode) {
        var couponApplications = couponUseCases.getApplications(couponCode);

        // Map from Domain to API
        var response = GetCouponApplicationsResponseJson.of(couponApplications);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/coupons")
    public ResponseEntity<CreateCouponResponseJson> createCoupon(@Valid @RequestBody CreateCouponRequestJson request) {

        // Map from API to Domain
        Coupon coupon = request.toCoupon();

        Coupon couponCreated = couponUseCases.createCoupon(coupon);

        // Map from Domain to API and return
        CreateCouponResponseJson response = CreateCouponResponseJson.of(couponCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/api/coupons/applications")
    public ResponseEntity<Object> applyCoupon(@Valid @RequestBody ApplyCouponRequestJson request) {

        // Map from API to Domain
        var basket = request.basket().toBasket();
        var couponCode = request.couponCode();

        var applicationResult = couponUseCases.applyCoupon(basket, couponCode);

        // Map from Domain to API and return
        ApplyCouponResponseJson response = ApplyCouponResponseJson.of(applicationResult);
        return ResponseEntity.ok(response);
    }

}

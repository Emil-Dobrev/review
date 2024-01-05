package it.schwarz.jobs.review.coupon.api;

import it.schwarz.jobs.review.coupon.domain.entity.AmountOfMoney;
import it.schwarz.jobs.review.coupon.domain.entity.Basket;
import it.schwarz.jobs.review.coupon.domain.entity.Coupon;
import it.schwarz.jobs.review.coupon.domain.usecase.CouponUseCases;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CouponRestController {

    private final CouponUseCases couponUseCases;


    public CouponRestController(CouponUseCases couponUseCases) {
        this.couponUseCases = couponUseCases;
    }


//    {
//        "timestamp": "2023-12-22T13:18:02.281+00:00",
//            "status": 400,
//            "error": "Bad Request",
//            "path": "/api/coupons/application"
//    }

//    @ExceptionHandler({ Exception.class })
//    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
//
//        ApiError apiError = new ApiError(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
//                request.get getContextPath(),
//                "errorCode",
//                "error occurred");
//
//        return new ResponseEntity<Object>(
//                apiError, new HttpHeaders(), apiError.getStatus());
//    }




    @GetMapping("/api/coupons")
    public ResponseEntity<GetCouponsResponseJson> getCoupons() {
        List<Coupon> coupons = couponUseCases.findAll();

        GetCouponsResponseJson response = new GetCouponsResponseJson(
                coupons.stream()
                .map(coupon -> new CouponJson(
                        coupon.getCode(),
                        coupon.getDiscount().toBigDecimal(),
                        coupon.getMinBasketValue().toBigDecimal(),
                        coupon.getApplicationCount()))
                .collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }


    @PostMapping("/api/coupons")
    public ResponseEntity<CreateCouponResponseJson> createCoupon(@Valid @RequestBody CreateCouponRequestJson request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        // Map from API to Domain
        CouponJson couponJson = request.coupon();
        Coupon coupon = new Coupon(
                couponJson.code(),
                AmountOfMoney.of(couponJson.discount()),
                AmountOfMoney.of(couponJson.minBasketValue()));

        Coupon couponCreated = couponUseCases.createCoupon(coupon);

        // Map from Domain to API
        CreateCouponResponseJson response = new CreateCouponResponseJson(
                new CouponJson(
                        couponCreated.getCode(),
                        couponCreated.getDiscount().toBigDecimal(),
                        couponCreated.getMinBasketValue().toBigDecimal(), 0));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/coupons/application")
    public ResponseEntity<Object> applyCoupon(@Valid @RequestBody ApplyCouponRequestJson request) {

        // Map from API to Domain
        var basketValue = AmountOfMoney.of(request.basketValue());
        var couponCode = request.couponCode();

        Basket basket = couponUseCases.applyCoupon(basketValue, couponCode);


        // Map from Domain to API
        ApplyCouponResponseJson response = new ApplyCouponResponseJson(
                new BasketJson(
                        basket.getValue().toBigDecimal(),
                        basket.getAppliedDiscount().toBigDecimal(),
                        basket.isApplicationSuccessful()));

        return ResponseEntity.ok(response);
    }

}

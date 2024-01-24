package it.schwarz.jobs.review.coupon.domain.usecase;

public class BusinessException extends RuntimeException {

    public BusinessException(String detail) {
        super(detail);
    }

    public BusinessException(String detail, Exception cause) {
        super(detail, cause);
    }

}

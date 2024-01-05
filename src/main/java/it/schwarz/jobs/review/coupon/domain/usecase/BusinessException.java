package it.schwarz.jobs.review.coupon.domain.usecase;

public class BusinessException extends RuntimeException {
    private String detail;

    public BusinessException(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}

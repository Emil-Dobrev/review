package it.schwarz.jobs.review.coupon.api;

public record ErrorResponseJson(
        String type,
        String title,
        int status,
        String detail,
        String instance
) {
}

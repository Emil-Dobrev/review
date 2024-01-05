package it.schwarz.jobs.review.coupon.api;

import it.schwarz.jobs.review.coupon.TestObjects;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CouponRestControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void testGetCouponOverview() throws Exception {

        GetCouponsResponseJson response = this.restTemplate
                .getForObject("http://localhost:" + port + "/api/coupons", GetCouponsResponseJson.class);

        assertThat(response.coupons()).hasSize(3);
    }

    @Test
    @Order(2)
    public void testCreateCoupon() throws Exception {

        CreateCouponRequestJson request = TestObjects.requests().validCoupon();
        CreateCouponResponseJson response = this.restTemplate
                .postForObject("http://localhost:" + port + "/api/coupons", request, CreateCouponResponseJson.class);

        assertThat(response.coupon()).isNotNull();
        assertThat(response.coupon().code()).isEqualTo(TestObjects.requests().validCoupon().coupon().code());

        GetCouponsResponseJson overviewResponse = this.restTemplate
                .getForObject("http://localhost:" + port + "/api/coupons", GetCouponsResponseJson.class);

        assertThat(overviewResponse.coupons()).hasSize(4);
    }

    @Test
    public void testApplicationWithValidData() throws Exception {

        ApplyCouponRequestJson request = TestObjects.requests().validApplication();
        ResponseEntity<ApplyCouponResponseJson> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/coupons/application", request, ApplyCouponResponseJson.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().basket().applicationSuccessful()).isTrue();
    }

    @Test
    public void testApplicationWithInvalidData() throws Exception {

        ApplyCouponRequestJson request = TestObjects.requests().invalidApplicationOfNotExistingCode();
        ResponseEntity<String> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/coupons/application", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("not found");
    }
}



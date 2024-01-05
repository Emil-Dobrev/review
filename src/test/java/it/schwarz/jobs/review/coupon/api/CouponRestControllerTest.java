package it.schwarz.jobs.review.coupon.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.schwarz.jobs.review.coupon.TestObjects;
import it.schwarz.jobs.review.coupon.domain.usecase.CouponUseCases;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CouponRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CouponUseCases couponUseCases;


    @Test
    void testGetCoupons() throws Exception {
        when(couponUseCases.findAll()).thenReturn(new ArrayList<>());

        this.mockMvc
                .perform(get("/api/coupons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("coupons")));
    }

    @Test
    void testCreateValidCoupon() throws Exception {

        CreateCouponRequestJson request = TestObjects.requests().validCoupon();
        when(couponUseCases.createCoupon(any())).thenReturn(TestObjects.coupons().COUPON_12_20());

        this.mockMvc
                .perform(post("/api/coupons")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testCreateInvalidCoupon() throws Exception {

        CreateCouponRequestJson request = TestObjects.requests().invalidCouponOfNegativeDiscount();

        this.mockMvc
                .perform(post("/api/coupons")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
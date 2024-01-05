package it.schwarz.jobs.review.coupon.domain;

import it.schwarz.jobs.review.coupon.domain.entity.AmountOfMoney;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmountOfMoneyTest {

    @Test
    void testZero() {
        assertThat(AmountOfMoney.ZERO().toBigDecimal()).isZero();
    }

    @Test
    void testIsGreaterThan() {
        var amount = AmountOfMoney.of("1.23");

        assertTrue(amount.isGreaterThan(AmountOfMoney.of("1.22")));
        assertTrue(amount.isGreaterThan(AmountOfMoney.of("1.00")));
        assertTrue(amount.isGreaterThan(AmountOfMoney.of("0")));
        assertTrue(amount.isGreaterThan(AmountOfMoney.of("-1.23")));

        assertFalse(amount.isGreaterThan(AmountOfMoney.of("1.23")));

        assertFalse(amount.isGreaterThan(AmountOfMoney.of("1.3")));
    }

    @Test
    void testIsLesserThan() {
        var amount = AmountOfMoney.of("1.23");

        assertFalse(amount.isLessThan(AmountOfMoney.of("1.22")));
        assertFalse(amount.isLessThan(AmountOfMoney.of("1.00")));
        assertFalse(amount.isLessThan(AmountOfMoney.of("0")));
        assertFalse(amount.isLessThan(AmountOfMoney.of("-1.23")));

        assertFalse(amount.isLessThan(AmountOfMoney.of("1.23")));

        assertTrue(amount.isLessThan(AmountOfMoney.of("1.3")));
    }
}
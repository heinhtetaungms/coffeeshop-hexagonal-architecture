package org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence;

import org.h2.mw.coffeeshop.core.application.out.Payments;
import org.h2.mw.coffeeshop.core.application.payment.CreditCard;
import org.h2.mw.coffeeshop.core.application.payment.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.h2.mw.application.payment.CreditCardTestFactory.aCreditCard;

@PersistenceTest
public class PaymentsJpaAdapterTest {
    @Autowired
    private Payments payments;

    @Test
    void creatingPaymentReturnsPersistedPayment() {
        var now = LocalDate.now();
        var creditCard = aCreditCard();
        var payment = new Payment(UUID.randomUUID(), creditCard, now);

        var persistedPayment = payments.save(payment);

        assertThat(persistedPayment.creditCard()).isEqualTo(creditCard);
        assertThat(persistedPayment.paid()).isEqualTo(now);
    }

    @Test
    @Sql("classpath:data/payment.sql")
    void findingPreviouslyPersistedPaymentReturnsDetails() {
        var payment = payments.findPaymentByOrderId(UUID.fromString("a41c9394-3aa6-4484-b0b4-87de55fa2cf4"));

        var expectedCreditCard = new CreditCard("Michael Faraday", "11223344", Month.JANUARY, Year.of(2023));

        assertThat(payment.creditCard()).isEqualTo(expectedCreditCard);
    }

    @Test
    void findingNonExistingPaymentThrowsException() {
        assertThatThrownBy(() -> payments.findPaymentByOrderId(UUID.randomUUID())).isInstanceOf(NoSuchElementException.class);
    }
}

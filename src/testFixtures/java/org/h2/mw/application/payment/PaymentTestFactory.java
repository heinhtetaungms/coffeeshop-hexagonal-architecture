package org.h2.mw.application.payment;

import org.h2.mw.coffeeshop.core.application.order.Order;
import org.h2.mw.coffeeshop.core.application.payment.Payment;

import java.time.LocalDate;


public class PaymentTestFactory {
    private PaymentTestFactory(){
    }

    public static Payment aPaymentForOrder(Order order) {
        return new Payment(order.getId(), CreditCardTestFactory.aCreditCard(), LocalDate.now());
    }
}

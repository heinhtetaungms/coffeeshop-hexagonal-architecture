package org.h2.mw.coffeeshop.core.application.out;

import org.h2.mw.coffeeshop.core.application.payment.Payment;

import java.util.UUID;

public interface Payments {
    Payment findPaymentByOrderId(UUID orderId);
    Payment save(Payment payment);
}

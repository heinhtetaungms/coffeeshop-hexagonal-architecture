package org.h2.mw.application.out.stub;

import org.h2.mw.coffeeshop.core.application.out.Payments;
import org.h2.mw.coffeeshop.core.application.payment.Payment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryPayments implements Payments {
    private final Map<UUID, Payment> entities = new HashMap<>();

    @Override
    public Payment findPaymentByOrderId(UUID orderId) {
        return entities.get(orderId);
    }

    @Override
    public Payment save(Payment payment) {
        entities.put(payment.orderId(), payment);
        return payment;
    }
}

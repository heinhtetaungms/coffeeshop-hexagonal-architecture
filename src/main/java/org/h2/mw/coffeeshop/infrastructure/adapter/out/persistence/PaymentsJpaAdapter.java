package org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.h2.mw.coffeeshop.core.application.out.Payments;
import org.h2.mw.coffeeshop.core.application.payment.Payment;
import org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentsJpaAdapter implements Payments {
    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment findPaymentByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrderId(orderId)
                .map(PaymentEntity::toDomain)
                .orElseThrow();
    }

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(PaymentEntity.fromDomain(payment)).toDomain();
    }
}

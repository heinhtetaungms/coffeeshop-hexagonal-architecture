package org.h2.mw.coffeeshop.infrastructure.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.h2.mw.coffeeshop.core.application.in.OrderingCoffee;
import org.h2.mw.coffeeshop.core.application.payment.CreditCard;
import org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource.PaymentRequest;
import org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Month;
import java.time.Year;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final OrderingCoffee orderingCoffee;

    @PutMapping("/payment/{id}")
    ResponseEntity<PaymentResponse> payOrder(@PathVariable UUID id, @Valid @RequestBody PaymentRequest request) {
        var payment = orderingCoffee.payOrder(id,
                new CreditCard(
                        request.cardHolderName(),
                        request.cardNumber(),
                        Month.of(request.expiryMonth()),
                        Year.of(request.expiryYear())
                )
        );
        return ResponseEntity.ok(PaymentResponse.fromDomain(payment));
    }
}

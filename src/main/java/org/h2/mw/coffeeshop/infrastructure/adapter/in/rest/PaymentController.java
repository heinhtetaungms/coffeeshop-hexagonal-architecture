package org.h2.mw.coffeeshop.infrastructure.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.h2.mw.coffeeshop.core.application.in.OrderingCoffee;
import org.h2.mw.coffeeshop.core.application.payment.CreditCard;
import org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource.PaymentRequest;
import org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource.PaymentResponse;
import org.h2.mw.coffeeshop.infrastructure.shared.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.Year;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final OrderingCoffee orderingCoffee;

    @PutMapping("/payment/{id}")
    ResponseEntity<ApiResponse<PaymentResponse>> payOrder(@PathVariable UUID id, @Valid @RequestBody PaymentRequest request) {
        var payment = orderingCoffee.payOrder(id,
                //TODO: need to implement Validation while building CreditCard
                new CreditCard(
                        request.cardHolderName(),
                        request.cardNumber(),
                        Month.of(request.expiryMonth()),
                        Year.of(request.expiryYear())
                )
        );
        return ApiResponse.resolve(HttpStatus.OK, PaymentResponse.fromDomain(payment));
    }
}

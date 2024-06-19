package org.h2.mw.coffeeshop.infrastructure.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.h2.mw.coffeeshop.core.application.in.OrderingCoffee;
import org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource.OrderResponse;
import org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource.ReceiptResponse;
import org.h2.mw.coffeeshop.infrastructure.shared.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReceiptController {
    private final OrderingCoffee orderingCoffee;

    @GetMapping("/receipt/{id}")
    ResponseEntity<ApiResponse<ReceiptResponse>> readReceipt(@PathVariable UUID id) {
        var receipt = orderingCoffee.readReceipt(id);
        return ApiResponse.resolve(HttpStatus.OK, ReceiptResponse.fromDomain(receipt));
    }

    @DeleteMapping("/receipt/{id}")
    ResponseEntity<ApiResponse<OrderResponse>> completeOrder(@PathVariable UUID id) {
        var order = orderingCoffee.takeOrder(id);
        return ApiResponse.resolve(HttpStatus.OK, OrderResponse.fromDomain(order));
    }
}

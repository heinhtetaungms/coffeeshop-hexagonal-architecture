package org.h2.mw.coffeeshop.infrastructure.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.h2.mw.coffeeshop.core.application.in.OrderingCoffee;
import org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource.OrderRequest;
import org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource.OrderResponse;
import org.h2.mw.coffeeshop.infrastructure.shared.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderingCoffee orderingCoffee;

    @PostMapping("/order")
    ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody OrderRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var order = orderingCoffee.placeOrder(request.toDomain());
        var location = uriComponentsBuilder.path("/order/{id}")
                .buildAndExpand(order.getId())
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(location);
        return ApiResponse.resolve(HttpStatus.CREATED, httpHeaders, OrderResponse.fromDomain(order));
    }

    @PostMapping("/order/{id}")
    ResponseEntity<ApiResponse<OrderResponse>> updateOrder(@PathVariable UUID id, @RequestBody OrderRequest request) {
        var order = orderingCoffee.updateOrder(id, request.toDomain());
        return ApiResponse.resolve(HttpStatus.OK, OrderResponse.fromDomain(order));
    }

    @DeleteMapping("/order/{id}")
    ResponseEntity<Void> cancelOrder(@PathVariable UUID id) {
        orderingCoffee.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
}

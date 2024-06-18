package org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource;

import org.h2.mw.coffeeshop.core.application.order.Order;
import org.h2.mw.coffeeshop.core.shared.Location;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Location location, List<LineItemResponse> items, BigDecimal cost) {
    public static OrderResponse fromDomain(Order order) {
        return new OrderResponse(
                order.getLocation(),
                order.getItems().stream().map(LineItemResponse::fromDomain).toList(),
                order.getCost()
        );
    }
}

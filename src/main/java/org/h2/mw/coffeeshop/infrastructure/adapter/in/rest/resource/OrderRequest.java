package org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource;

import org.h2.mw.coffeeshop.core.application.order.Order;
import org.h2.mw.coffeeshop.core.shared.Location;

import java.util.List;

public record OrderRequest(Location location, List<LineItemRequest> items) {
    public Order toDomain() {
        return new Order(location, items.stream().map(LineItemRequest::toDomain).toList());
    }
}

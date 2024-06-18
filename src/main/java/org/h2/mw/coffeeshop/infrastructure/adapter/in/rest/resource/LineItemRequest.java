package org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource;

import org.h2.mw.coffeeshop.core.application.order.LineItem;
import org.h2.mw.coffeeshop.core.shared.Drink;
import org.h2.mw.coffeeshop.core.shared.Milk;
import org.h2.mw.coffeeshop.core.shared.Size;

public record LineItemRequest(Drink drink, Milk milk, Size size, Integer quantity) {
    public LineItem toDomain() {
        return new LineItem(drink, milk, size, quantity);
    }
}

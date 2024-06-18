package org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource;

import org.h2.mw.coffeeshop.core.application.order.LineItem;
import org.h2.mw.coffeeshop.core.shared.Drink;
import org.h2.mw.coffeeshop.core.shared.Milk;
import org.h2.mw.coffeeshop.core.shared.Size;

public record LineItemResponse(Drink drink, Milk milk, Size size, Integer quantity) {
    public static LineItemResponse fromDomain(LineItem lineItem) {
        return new LineItemResponse(
                lineItem.getDrink(),
                lineItem.getMilk(),
                lineItem.getSize(),
                lineItem.getQuantity()
        );
    }
}

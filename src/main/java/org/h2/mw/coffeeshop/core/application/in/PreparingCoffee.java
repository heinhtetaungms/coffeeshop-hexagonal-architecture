package org.h2.mw.coffeeshop.core.application.in;

import org.h2.mw.coffeeshop.core.application.order.Order;

import java.util.UUID;

public interface PreparingCoffee {
    Order startPreparingOrder(UUID orderId);
    Order finishPreparingOrder(UUID orderId);
}

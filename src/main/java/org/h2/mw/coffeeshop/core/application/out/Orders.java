package org.h2.mw.coffeeshop.core.application.out;

import org.h2.mw.coffeeshop.core.application.order.Order;

import java.util.UUID;

public interface Orders {
    Order findOrderById(UUID orderId) throws OrderNotFound;
    Order save(Order order);
    void deleteById(UUID orderId);
}

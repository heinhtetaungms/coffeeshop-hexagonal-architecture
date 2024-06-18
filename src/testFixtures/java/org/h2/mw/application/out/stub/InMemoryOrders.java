package org.h2.mw.application.out.stub;

import org.h2.mw.coffeeshop.core.application.order.Order;
import org.h2.mw.coffeeshop.core.application.out.OrderNotFound;
import org.h2.mw.coffeeshop.core.application.out.Orders;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryOrders implements Orders {
    private final Map<UUID, Order> entities = new HashMap<>();

    @Override
    public Order findOrderById(UUID orderId) {
        var order = entities.get(orderId);
        if (order == null) {
            throw new OrderNotFound();
        }
        return order;
    }

    @Override
    public Order save(Order order) {
        entities.put(order.getId(), order);
        return order;
    }

    @Override
    public void deleteById(UUID orderId) {
        entities.remove(orderId);
    }
}

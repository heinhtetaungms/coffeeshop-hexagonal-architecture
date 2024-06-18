package org.h2.mw.coffeeshop.core.application;

import org.h2.mw.coffeeshop.architecture.UseCase;
import org.h2.mw.coffeeshop.core.application.in.PreparingCoffee;
import org.h2.mw.coffeeshop.core.application.order.Order;
import org.h2.mw.coffeeshop.core.application.out.Orders;

import java.util.UUID;

@UseCase
public class CoffeeMachine implements PreparingCoffee {
    private final Orders orders;

    public CoffeeMachine(Orders orders) {
        this.orders = orders;
    }

    @Override
    public Order startPreparingOrder(UUID orderId) {
        var order = orders.findOrderById(orderId);

        return orders.save(order.markBeingPrepared());
    }

    @Override
    public Order finishPreparingOrder(UUID orderId) {
        var order = orders.findOrderById(orderId);

        return orders.save(order.markPrepared());
    }
}

package org.h2.mw.coffeeshop.core.application;


import org.h2.mw.coffeeshop.architecture.UseCase;
import org.h2.mw.coffeeshop.core.application.in.OrderingCoffee;
import org.h2.mw.coffeeshop.core.application.order.Order;
import org.h2.mw.coffeeshop.core.application.out.Orders;
import org.h2.mw.coffeeshop.core.application.out.Payments;
import org.h2.mw.coffeeshop.core.application.payment.CreditCard;
import org.h2.mw.coffeeshop.core.application.payment.Payment;
import org.h2.mw.coffeeshop.core.application.payment.Receipt;

import java.time.LocalDate;
import java.util.UUID;

@UseCase
public class CoffeeShop implements OrderingCoffee {
    private final Orders orders;
    private final Payments payments;

    public CoffeeShop(Orders orders, Payments payments) {
        this.orders = orders;
        this.payments = payments;
    }

    @Override
    public Order placeOrder(Order order) {
        return orders.save(order);
    }

    @Override
    public Order updateOrder(UUID orderId, Order order) {
        var existingOrder = orders.findOrderById(orderId);

        return orders.save(existingOrder.update(order));
    }

    @Override
    public void cancelOrder(UUID orderId) {
        var order = orders.findOrderById(orderId);

        if (!order.canBeCancelled()) {
            throw new IllegalStateException("Order is already paid");
        }

        orders.deleteById(orderId);
    }

    @Override
    public Payment payOrder(UUID orderId, CreditCard creditCard) {
        var order = orders.findOrderById(orderId);

        orders.save(order.markPaid());

        return payments.save(new Payment(orderId, creditCard, LocalDate.now()));
    }

    @Override
    public Receipt readReceipt(UUID orderId) {
        var order = orders.findOrderById(orderId);
        var payment = payments.findPaymentByOrderId(orderId);

        return new Receipt(order.getCost(), payment.paid());
    }

    @Override
    public Order takeOrder(UUID orderId) {
        var order = orders.findOrderById(orderId);

        return orders.save(order.markTaken());
    }
}

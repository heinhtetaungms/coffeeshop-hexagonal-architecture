package org.h2.mw.coffeeshop.core.application.in;

import org.h2.mw.coffeeshop.core.application.order.Order;
import org.h2.mw.coffeeshop.core.application.payment.CreditCard;
import org.h2.mw.coffeeshop.core.application.payment.Payment;
import org.h2.mw.coffeeshop.core.application.payment.Receipt;

import java.util.UUID;

public interface OrderingCoffee {
    Order placeOrder(Order order);
    Order updateOrder(UUID orderId, Order order);
    void cancelOrder(UUID orderId);
    Payment payOrder(UUID orderId, CreditCard creditCard);
    Receipt readReceipt(UUID orderId);
    Order takeOrder(UUID orderId);
}

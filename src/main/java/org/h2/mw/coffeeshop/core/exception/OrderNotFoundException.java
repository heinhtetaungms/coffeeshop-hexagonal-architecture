package org.h2.mw.coffeeshop.core.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(UUID orderId) {
        super("Order Not found by Order ID: " + orderId);
    }
}

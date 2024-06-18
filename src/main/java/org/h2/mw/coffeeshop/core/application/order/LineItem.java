package org.h2.mw.coffeeshop.core.application.order;

import org.h2.mw.coffeeshop.core.shared.Drink;
import org.h2.mw.coffeeshop.core.shared.Milk;
import org.h2.mw.coffeeshop.core.shared.Size;

import java.math.BigDecimal;

public record LineItem(Drink drink, Milk milk, Size size, int quantity) {
    // For simplicity every small drink costs 4.0 and large 5.0
    BigDecimal getCost() {
        var price = BigDecimal.valueOf(4.0);
        if (size == Size.LARGE) {
            price = price.add(BigDecimal.ONE);
        }
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

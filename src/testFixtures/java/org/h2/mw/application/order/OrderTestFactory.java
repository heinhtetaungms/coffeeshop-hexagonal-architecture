package org.h2.mw.application.order;

import org.h2.mw.coffeeshop.core.application.order.LineItem;
import org.h2.mw.coffeeshop.core.application.order.Order;
import org.h2.mw.coffeeshop.core.shared.Drink;
import org.h2.mw.coffeeshop.core.shared.Location;
import org.h2.mw.coffeeshop.core.shared.Milk;
import org.h2.mw.coffeeshop.core.shared.Size;

import java.util.List;

public class OrderTestFactory {
    public static Order anOrder() {
        return new Order(Location.TAKE_AWAY, List.of(new LineItem(Drink.LATTE, Milk.WHOLE, Size.LARGE, 1)));
    }

    public static Order aPaidOrder() {
        return anOrder()
                .markPaid();
    }

    public static Order anOrderInPreparation() {
        return aPaidOrder()
                .markBeingPrepared();
    }

    public static Order aReadyOrder() {
        return anOrderInPreparation()
                .markPrepared();
    }
}

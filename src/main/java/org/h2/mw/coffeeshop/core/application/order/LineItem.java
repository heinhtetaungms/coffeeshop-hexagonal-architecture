package org.h2.mw.coffeeshop.core.application.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.h2.mw.coffeeshop.core.shared.Drink;
import org.h2.mw.coffeeshop.core.shared.Milk;
import org.h2.mw.coffeeshop.core.shared.Size;
import org.h2.mw.coffeeshop.infrastructure.shared.SelfValidating;

import java.math.BigDecimal;

@Data
public class LineItem extends SelfValidating<LineItem> {
    @NotNull(message = "Drink must be specified")
    private final Drink drink;
    @NotNull(message = "Milk must be specified")
    private final Milk milk;
    @NotNull(message = "Size must be specified")
    private final Size size;
    @Min(value = 1, message = "Quantity must be at least 1")
    private final int quantity;

    public LineItem(Drink drink, Milk milk, Size size, int quantity) {
        this.drink = drink;
        this.milk = milk;
        this.size = size;
        this.quantity = quantity;
        validateSelf();
    }

    // For simplicity: every small drink costs 4.0 and large 5.0
    public BigDecimal getCost() {
        BigDecimal price = BigDecimal.valueOf(4.0);
        if (size == Size.LARGE) {
            price = price.add(BigDecimal.ONE);
        }
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

package org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.h2.mw.coffeeshop.core.application.order.LineItem;
import org.h2.mw.coffeeshop.core.shared.Drink;
import org.h2.mw.coffeeshop.core.shared.Milk;
import org.h2.mw.coffeeshop.core.shared.Size;

import java.util.UUID;

@Entity
@Setter
@Getter
public class LineItemEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated
    @NotNull
    private Drink drink;

    @Enumerated
    @NotNull
    private Size size;

    @Enumerated
    @NotNull
    private Milk milk;

    @NotNull
    private Integer quantity;

    public LineItem toDomain() {
        return new LineItem(
                drink,
                milk,
                size,
                quantity
        );
    }

    public static LineItemEntity fromDomain(LineItem lineItem) {
        var entity = new LineItemEntity();
        entity.setDrink(lineItem.drink());
        entity.setQuantity(lineItem.quantity());
        entity.setMilk(lineItem.milk());
        entity.setSize(lineItem.size());
        return entity;
    }
}

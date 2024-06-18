package org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.h2.mw.coffeeshop.core.application.order.Order;
import org.h2.mw.coffeeshop.core.application.out.OrderNotFound;
import org.h2.mw.coffeeshop.core.application.out.Orders;
import org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrdersJpaAdapter implements Orders {
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order findOrderById(UUID orderId) {
        return orderJpaRepository.findById(orderId)
                .map(OrderEntity::toDomain)
                .orElseThrow(OrderNotFound::new);
    }

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(OrderEntity.fromDomain(order)).toDomain();
    }

    @Override
    public void deleteById(UUID orderId) {
        orderJpaRepository.deleteById(orderId);
    }
}

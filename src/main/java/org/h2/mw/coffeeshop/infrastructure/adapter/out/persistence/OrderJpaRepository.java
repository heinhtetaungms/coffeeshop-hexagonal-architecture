package org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence;


import org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
}

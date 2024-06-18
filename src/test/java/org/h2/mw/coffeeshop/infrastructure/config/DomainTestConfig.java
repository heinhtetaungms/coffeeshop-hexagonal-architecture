package org.h2.mw.coffeeshop.infrastructure.config;

import org.h2.mw.application.out.stub.InMemoryOrders;
import org.h2.mw.application.out.stub.InMemoryPayments;
import org.h2.mw.coffeeshop.core.application.out.Orders;
import org.h2.mw.coffeeshop.core.application.out.Payments;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(DomainConfig.class)
public class DomainTestConfig {
    @Bean
    Orders orders() {
        return new InMemoryOrders();
    }

    @Bean
    Payments payments() {
        return new InMemoryPayments();
    }
}

package org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence.config;

import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

public class TransactionalUseCaseExecutor {
    @Transactional
    public <T> T executeInTransaction(Supplier<T> execution) {
        return execution.get();
    }
}

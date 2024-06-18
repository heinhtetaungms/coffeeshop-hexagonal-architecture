package org.h2.mw.coffeeshop.infrastructure.adapter.in.rest.resource;

import org.h2.mw.coffeeshop.core.application.payment.Receipt;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceiptResponse(BigDecimal amount, LocalDate paid) {
    public static ReceiptResponse fromDomain(Receipt receipt) {
        return new ReceiptResponse(receipt.amount(), receipt.paid());
    }
}

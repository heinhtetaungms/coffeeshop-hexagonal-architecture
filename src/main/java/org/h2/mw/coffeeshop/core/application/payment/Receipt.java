package org.h2.mw.coffeeshop.core.application.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Receipt(BigDecimal amount, LocalDate paid) { }

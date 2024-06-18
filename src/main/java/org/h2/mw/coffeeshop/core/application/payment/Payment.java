package org.h2.mw.coffeeshop.core.application.payment;

import java.time.LocalDate;
import java.util.UUID;

public record Payment(UUID orderId, CreditCard creditCard, LocalDate paid) { }

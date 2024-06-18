package org.h2.mw.coffeeshop.infrastructure.adapter.in.rest;

import org.h2.mw.coffeeshop.core.application.out.Orders;
import org.h2.mw.coffeeshop.core.application.out.Payments;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.h2.mw.application.order.OrderTestFactory.aReadyOrder;
import static org.h2.mw.application.order.OrderTestFactory.anOrder;
import static org.h2.mw.application.payment.PaymentTestFactory.aPaymentForOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RestResourceTest
class ReceiptControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Orders orders;

    @Autowired
    private Payments payments;

    @Test
    void readReceipt() throws Exception {
        var order = orders.save(anOrder());
        payments.save(aPaymentForOrder(order));

        mockMvc.perform(get("/receipt/{id}", order.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void completeOrder() throws Exception {
        var order = orders.save(aReadyOrder());

        mockMvc.perform(delete("/receipt/{id}", order.getId()))
                .andExpect(status().isOk());
    }
}

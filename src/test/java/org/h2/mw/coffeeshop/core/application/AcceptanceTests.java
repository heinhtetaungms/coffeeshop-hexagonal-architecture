package org.h2.mw.coffeeshop.core.application;

import org.h2.mw.application.out.stub.InMemoryOrders;
import org.h2.mw.application.out.stub.InMemoryPayments;
import org.h2.mw.coffeeshop.core.application.in.*;
import org.h2.mw.coffeeshop.core.application.order.*;
import org.h2.mw.coffeeshop.core.application.out.*;
import org.h2.mw.coffeeshop.core.shared.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.h2.mw.application.order.OrderTestFactory.*;
import static org.h2.mw.application.payment.CreditCardTestFactory.aCreditCard;
import static org.h2.mw.application.payment.PaymentTestFactory.aPaymentForOrder;

class AcceptanceTests {
    private Orders orders;
    private Payments payments;
    private OrderingCoffee customer;
    private PreparingCoffee barista;

    @BeforeEach
    void setup() {
        orders = new InMemoryOrders();
        payments = new InMemoryPayments();
        customer = new CoffeeShop(orders, payments);
        barista = new CoffeeMachine(orders);
    }

    @Test
    void customerCanOrderCoffee() {
        var orderToMake = new Order(Location.IN_STORE, List.of(new LineItem(Drink.CAPPUCCINO, Milk.SKIMMED, Size.SMALL, 1)));

        var order = customer.placeOrder(orderToMake);

        assertThat(order.getLocation()).isEqualTo(Location.IN_STORE);
        assertThat(order.getItems()).containsExactly(new LineItem(Drink.CAPPUCCINO, Milk.SKIMMED, Size.SMALL, 1));
        assertThat(order.getStatus()).isEqualTo(Status.PAYMENT_EXPECTED);
    }

    @Test
    void customerCanUpdateTheOrderBeforePaying() {
        var orderWithOneItem = new Order(Location.TAKE_AWAY, List.of(new LineItem(Drink.LATTE, Milk.WHOLE, Size.LARGE, 1)));
        var orderWithTwoItems = new Order(Location.TAKE_AWAY, List.of(new LineItem(Drink.LATTE, Milk.WHOLE, Size.LARGE, 2)));

        var order = customer.placeOrder(orderWithOneItem);
        var updatedOrder = customer.updateOrder(order.getId(), orderWithTwoItems);

        assertThat(updatedOrder.getItems()).containsExactly(new LineItem(Drink.LATTE, Milk.WHOLE, Size.LARGE, 2));
    }

    @Test
    void customerCanCancelTheOrderBeforePaying() {
        var existingOrder = orders.save(anOrder());

        customer.cancelOrder(existingOrder.getId());

        assertThatThrownBy(() -> orders.findOrderById(existingOrder.getId())).isInstanceOf(OrderNotFound.class);
    }

    @Test
    void customerCanPayTheOrder() {
        var existingOrder = orders.save(anOrder());
        var creditCard = aCreditCard();

        var payment = customer.payOrder(existingOrder.getId(), creditCard);

        assertThat(payment.orderId()).isEqualTo(existingOrder.getId());
        assertThat(payment.creditCard()).isEqualTo(creditCard);
        assertThat(orders.findOrderById(existingOrder.getId()).getStatus()).isEqualTo(Status.PAID);
    }

    @Test
    void noChangesAllowedWhenOrderIsPaid() {
        var existingOrder = orders.save(aPaidOrder());

        assertThatThrownBy(() -> customer.updateOrder(existingOrder.getId(), anOrder())).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void customerCanGetReceiptWhenOrderIsPaid() {
        var existingOrder = orders.save(aPaidOrder());
        var existingPayment = payments.save(aPaymentForOrder(existingOrder));

        var receipt = customer.readReceipt(existingOrder.getId());

        assertThat(receipt.amount()).isEqualTo(existingOrder.getCost());
        assertThat(receipt.paid()).isEqualTo(existingPayment.paid());
    }

    @Test
    void baristaCanStartPreparingTheOrderWhenItIsPaid() {
        var existingOrder = orders.save(aPaidOrder());

        var orderInPreparation = barista.startPreparingOrder(existingOrder.getId());

        assertThat(orderInPreparation.getStatus()).isEqualTo(Status.PREPARING);
    }

    @Test
    void baristaCanMarkTheOrderReadyWhenSheIsFinishedPreparing() {
        var existingOrder = orders.save(anOrderInPreparation());

        var preparedOrder = barista.finishPreparingOrder(existingOrder.getId());

        assertThat(preparedOrder.getStatus()).isEqualTo(Status.READY);
    }

    @Test
    void customerCanTakeTheOrderWhenItIsReady() {
        var existingOrder = orders.save(aReadyOrder());

        var takenOrder = customer.takeOrder(existingOrder.getId());

        assertThat(takenOrder.getStatus()).isEqualTo(Status.TAKEN);
    }
}

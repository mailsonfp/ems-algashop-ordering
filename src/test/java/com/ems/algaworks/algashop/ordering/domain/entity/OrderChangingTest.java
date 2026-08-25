package com.ems.algaworks.algashop.ordering.domain.entity;

import com.ems.algaworks.algashop.ordering.domain.entity.data.OrderTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.entity.data.ProductTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.exception.OrderCannotBeEditedException;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Billing;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Quantity;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Shipping;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderChangingTest {

    @Test
    void givenDraftOrder_whenChangeIsPerformed_shouldNotThrowException() {
        Order draftOrder = OrderTestDataBuilder.anOrder().build();

        Product product = ProductTestDataBuilder.aProductAltMousePad().build();
        Quantity quantity = new Quantity(2);
        Billing billing = OrderTestDataBuilder.aBilling();
        Shipping shipping = OrderTestDataBuilder.aShipping();
        PaymentMethod method = PaymentMethod.CREDIT_CARD;

        OrderItem orderItem = draftOrder.items().iterator().next();

        Assertions.assertThatCode(() -> draftOrder.addItem(product, quantity)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> draftOrder.changeBilling(billing)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> draftOrder.changeShipping(shipping)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> draftOrder.changeItemQuantity(orderItem.id(), quantity)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> draftOrder.changePaymentMethod(method)).doesNotThrowAnyException();
    }

    @Test
    void givenPlacedOrder_whenChangeBillingIsCalled_shouldThrowOrderCannotBeEditedException() {
        Order placedOrder = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Billing billing = OrderTestDataBuilder.aBilling();

        Assertions.assertThatThrownBy(() -> placedOrder.changeBilling(billing))
                .isInstanceOf(OrderCannotBeEditedException.class);
    }

    @Test
    void givenPlacedOrder_whenChangeShippingIsCalled_shouldThrowOrderCannotBeEditedException() {
        Order placedOrder = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Shipping shipping = OrderTestDataBuilder.aShipping();

        Assertions.assertThatThrownBy(() -> placedOrder.changeShipping(shipping))
                .isInstanceOf(OrderCannotBeEditedException.class);
    }

    @Test
    void givenPlacedOrder_whenChangeItemQuantityIsCalled_shouldThrowOrderCannotBeEditedException() {
        Order placedOrder = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Quantity quantity = new Quantity(5);

        OrderItem orderItem = placedOrder.items().iterator().next();

        Assertions.assertThatThrownBy(() -> placedOrder.changeItemQuantity(orderItem.id(), quantity))
                .isInstanceOf(OrderCannotBeEditedException.class);
    }

    @Test
    void givenPlacedOrder_whenChangePaymentMethodIsCalled_shouldThrowOrderCannotBeEditedException() {
        Order placedOrder = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        PaymentMethod method = PaymentMethod.GATEWAY_BALANCE;

        Assertions.assertThatThrownBy(() -> placedOrder.changePaymentMethod(method))
                .isInstanceOf(OrderCannotBeEditedException.class);
    }

}

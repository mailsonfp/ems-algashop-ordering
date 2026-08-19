package com.ems.algaworks.algashop.ordering.domain.entity;

import com.ems.algaworks.algashop.ordering.domain.entity.data.ProductTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Money;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.OrderId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Quantity;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.Product;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.ProductId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.ProductName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderItemTest {

    @Test
    public void shouldGenerateBrandNewOrderItem() {
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(1);
        OrderId orderId = new OrderId();

        OrderItem orderItem = OrderItem.brandNew()
                .product(product)
                .quantity(quantity)
                .orderId(orderId)
                .build();

        Assertions.assertWith(orderItem,
                o-> Assertions.assertThat(o.id()).isNotNull(),
                o-> Assertions.assertThat(o.productId()).isEqualTo(product.id()),
                o-> Assertions.assertThat(o.productName()).isEqualTo(product.name()),
                o-> Assertions.assertThat(o.price()).isEqualTo(product.price()),
                o-> Assertions.assertThat(o.quantity()).isEqualTo(quantity),
                o-> Assertions.assertThat(o.orderId()).isEqualTo(orderId)
        );
    }
}

package com.ems.algaworks.algashop.ordering.domain.entity;

import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Money;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.OrderId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Quantity;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.ProductId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.ProductName;
import org.junit.jupiter.api.Test;

public class OrderItemTest {

    @Test
    public void shouldGenerate() {
        OrderItem.brandNew()
                .productId(new ProductId())
                .quantity(new Quantity(1))
                .orderId(new OrderId())
                .productName(new ProductName("Mouse pad"))
                .price(new Money("100"))
                .build();
    }
}

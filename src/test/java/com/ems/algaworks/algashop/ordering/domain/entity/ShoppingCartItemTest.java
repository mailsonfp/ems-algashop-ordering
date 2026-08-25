package com.ems.algaworks.algashop.ordering.domain.entity;

import com.ems.algaworks.algashop.ordering.domain.entity.data.ProductTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Money;
import com.ems.algaworks.algashop.ordering.domain.valueobject.order.Quantity;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.Product;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.ProductId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.ProductName;
import com.ems.algaworks.algashop.ordering.domain.valueobject.shoppingcart.ShoppingCartId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.shoppingcart.ShoppingCartItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShoppingCartItemTest {

    @Test
    public void givenValidData_whenCreateNewItem_shouldInitializeCorrectly() {
        ShoppingCartItem item = ShoppingCartItemTestDataBuilder.aShoppingCartItem()
                .productName(new ProductName("Notebook"))
                .price(new Money("2000"))
                .quantity(new Quantity(2))
                .available(true)
                .build();

        Assertions.assertWith(item,
                i -> Assertions.assertThat(i.id()).isNotNull(),
                i -> Assertions.assertThat(i.shoppingCartId()).isNotNull(),
                i -> Assertions.assertThat(i.productId()).isNotNull(),
                i -> Assertions.assertThat(i.name()).isEqualTo(new ProductName("Notebook")),
                i -> Assertions.assertThat(i.price()).isEqualTo(new Money("2000")),
                i -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(2)),
                i -> Assertions.assertThat(i.isAvailable()).isTrue(),
                i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("4000"))
        );
    }

    @Test
    public void givenItem_whenChangeQuantity_shouldRecalculateTotal() {
        ShoppingCartItem item = ShoppingCartItemTestDataBuilder.aShoppingCartItem()
                .price(new Money("1000"))
                .quantity(new Quantity(1))
                .build();

        item.changeQuantity(new Quantity(3));

        Assertions.assertWith(item,
                i -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(3)),
                i -> Assertions.assertThat(i.totalAmount()).isEqualTo(new Money("3000"))
        );
    }

    @Test
    public void givenEqualIds_whenCompareItems_shouldBeEqual() {
        ShoppingCartId cartId = new ShoppingCartId();
        ProductId productId = new ProductId();
        ShoppingCartItemId shoppingCartItemId = new ShoppingCartItemId();

        ShoppingCartItem item1 = ShoppingCartItem.existing()
                .id(shoppingCartItemId)
                .shoppingCartId(cartId)
                .productId(productId)
                .productName(new ProductName("Mouse"))
                .price(new Money("100"))
                .quantity(new Quantity(1))
                .available(true)
                .totalAmount(new Money("100"))
                .build();

        ShoppingCartItem item2 = ShoppingCartItem.existing()
                .id(shoppingCartItemId)
                .shoppingCartId(cartId)
                .productId(productId)
                .productName(new ProductName("Notebook"))
                .price(new Money("100"))
                .quantity(new Quantity(1))
                .available(true)
                .totalAmount(new Money("100"))
                .build();

        Assertions.assertThat(item1).isEqualTo(item2);
        Assertions.assertThat(item1.hashCode()).isEqualTo(item2.hashCode());
    }

    @Test
    public void givenDifferentIds_whenCompareItems_shouldNotBeEqual() {
        ShoppingCartItem item1 = ShoppingCartItemTestDataBuilder.aShoppingCartItem().build();
        ShoppingCartItem item2 = ShoppingCartItemTestDataBuilder.aShoppingCartItem().build();

        Assertions.assertThat(item1).isNotEqualTo(item2);
    }

}

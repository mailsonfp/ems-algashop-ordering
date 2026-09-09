package com.ems.algaworks.algashop.ordering.domain.model.valueobject.product;

import com.ems.algaworks.algashop.ordering.domain.model.exception.ProductOutOfStockException;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.Money;
import lombok.Builder;

import java.util.Objects;

@Builder
public record Product(
    ProductId id,
    ProductName name,
    Money price,
    Boolean inStock
) {
    public Product {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
        Objects.requireNonNull(inStock);
    }

    public void checkOutOfStock() {
        if (isOutOfStock()) {
            throw new ProductOutOfStockException(this.id());
        }
    }

    private Boolean isOutOfStock() {
        return !inStock;
    }
}

package com.ems.algaworks.algashop.ordering.domain.exception;

import com.ems.algaworks.algashop.ordering.domain.valueobject.product.ProductId;

public class ProductOutOfStockException extends DomainException {
    public ProductOutOfStockException(ProductId id) {
        super(String.format(ErrorMessages.ERROR_PRODUCT_OUT_OF_STOCK, id.value()));
    }
}

package com.ems.algaworks.algashop.ordering.domain.valueobject.shoppingcart;

import com.ems.algaworks.algashop.ordering.domain.exception.DomainException;
import com.ems.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.ems.algaworks.algashop.ordering.domain.valueobject.product.ProductId;

public class ShoppingCartDoesNotContainProductException extends DomainException {
    public ShoppingCartDoesNotContainProductException(ShoppingCartId id, ProductId productId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT, id, productId));
    }
}

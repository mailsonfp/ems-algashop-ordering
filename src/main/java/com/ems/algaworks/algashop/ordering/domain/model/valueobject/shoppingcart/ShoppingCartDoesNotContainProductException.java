package com.ems.algaworks.algashop.ordering.domain.model.valueobject.shoppingcart;

import com.ems.algaworks.algashop.ordering.domain.model.exception.DomainException;
import com.ems.algaworks.algashop.ordering.domain.model.exception.ErrorMessages;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.ProductId;

public class ShoppingCartDoesNotContainProductException extends DomainException {
    public ShoppingCartDoesNotContainProductException(ShoppingCartId id, ProductId productId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT, id, productId));
    }
}

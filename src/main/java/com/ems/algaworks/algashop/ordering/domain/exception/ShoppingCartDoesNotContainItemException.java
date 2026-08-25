package com.ems.algaworks.algashop.ordering.domain.exception;

import com.ems.algaworks.algashop.ordering.domain.valueobject.shoppingcart.ShoppingCartId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.shoppingcart.ShoppingCartItemId;

public class ShoppingCartDoesNotContainItemException extends DomainException {
    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ShoppingCartItemId shoppingCartItemId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM, id, shoppingCartItemId));
    }
}

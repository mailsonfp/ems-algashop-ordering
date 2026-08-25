package com.ems.algaworks.algashop.ordering.domain.exception;

import com.ems.algaworks.algashop.ordering.domain.valueobject.product.ProductId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.shoppingcart.ShoppingCartItemId;

public class ShoppingCartItemIncompatibleProductException extends DomainException {
    public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id, ProductId productId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT, id, productId));
    }
}

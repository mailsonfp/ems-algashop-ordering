package com.ems.algaworks.algashop.ordering.domain.model.exception;

import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.ProductId;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.shoppingcart.ShoppingCartItemId;

public class ShoppingCartItemIncompatibleProductException extends DomainException {
    public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id, ProductId productId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT, id, productId));
    }
}

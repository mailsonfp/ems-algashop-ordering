package com.ems.algaworks.algashop.ordering.domain.model.repository;

import com.ems.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.shoppingcart.ShoppingCartId;

import java.util.Optional;

public interface ShoppingCarts extends RemoveCapableRepository<ShoppingCart, ShoppingCartId> {

    Optional<ShoppingCart> ofCustomer(CustomerId customerId);
}

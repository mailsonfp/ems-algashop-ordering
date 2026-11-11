package com.ems.algaworks.algashop.ordering.infrastructure.service.shopping.cart;

import com.ems.algaworks.algashop.ordering.domain.model.service.shopping.cart.ShoppingCartProductAdjustmentService;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.Money;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.ProductId;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.repository.ShoppingCartPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ShoppingCartProductAdjustmentServiceImpl implements ShoppingCartProductAdjustmentService {
    private final ShoppingCartPersistenceEntityRepository shoppingCartPersistenceEntityRepository;

    @Override
    @Transactional
    public void adjustPrice(ProductId productId, Money updatedPrice) {
        shoppingCartPersistenceEntityRepository.updateItemPrice(productId.value(), updatedPrice.value());
        shoppingCartPersistenceEntityRepository.recalculateTotalsForCartsWithProduct(productId.value());
    }

    @Override
    @Transactional
    public void changeAvailability(ProductId productId, boolean available) {
        shoppingCartPersistenceEntityRepository.updateItemAvailability(productId.value(), available);
    }
}

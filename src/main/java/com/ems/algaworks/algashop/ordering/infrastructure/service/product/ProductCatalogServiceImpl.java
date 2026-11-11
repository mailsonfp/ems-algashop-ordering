package com.ems.algaworks.algashop.ordering.infrastructure.service.product;

import com.ems.algaworks.algashop.ordering.domain.model.service.product.ProductCatalogService;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.Money;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.Product;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.ProductId;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.ProductName;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {
    @Override
    public Optional<Product> ofId(ProductId productId) {
        Product product = Product.builder().id(productId)
                .inStock(true)
                .name(new ProductName("Notebook"))
                .price(new Money("3000"))
                .build();
        return Optional.of(product);
    }
}

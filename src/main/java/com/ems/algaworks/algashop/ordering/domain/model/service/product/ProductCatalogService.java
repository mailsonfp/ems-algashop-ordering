package com.ems.algaworks.algashop.ordering.domain.model.service.product;

import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.Product;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.ProductId;

import java.util.Optional;

public interface ProductCatalogService {
    Optional<Product> ofId(ProductId productId);
}

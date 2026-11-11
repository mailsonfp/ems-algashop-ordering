package com.ems.algaworks.algashop.ordering.infrastructure.provider;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.ems.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.ems.algaworks.algashop.ordering.domain.model.entity.ShoppingCartItem;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.CustomerTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.ProductTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.ShoppingCartTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.service.shopping.cart.ShoppingCartProductAdjustmentService;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.Money;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.Quantity;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.Product;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.ProductId;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditConfig;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.provider.CustomersPersistenceProvider;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.provider.ShoppingCartsPersistenceProvider;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.repository.ShoppingCartPersistenceEntityRepository;
import com.ems.algaworks.algashop.ordering.infrastructure.service.shopping.cart.ShoppingCartProductAdjustmentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest(properties = "algashop.h2-console.enabled=false")
@Import({
        ShoppingCartsPersistenceProvider.class,
        CustomersPersistenceProvider.class,
        SpringDataAuditConfig.class
})
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ShoppingCartsPersistenceUpdateProviderIT {

    private ShoppingCartsPersistenceProvider persistenceProvider;
    private CustomersPersistenceProvider customersPersistenceProvider;
    private ShoppingCartPersistenceEntityRepository entityRepository;

    private ShoppingCartProductAdjustmentServiceImpl shoppingCartUpdateProvider;

    @Autowired
    public ShoppingCartsPersistenceUpdateProviderIT(
            ShoppingCartsPersistenceProvider persistenceProvider,
            CustomersPersistenceProvider customersPersistenceProvider,
            ShoppingCartPersistenceEntityRepository entityRepository,
            ShoppingCartProductAdjustmentServiceImpl shoppingCartUpdateProvider
    ) {
        this.persistenceProvider = persistenceProvider;
        this.customersPersistenceProvider = customersPersistenceProvider;
        this.entityRepository = entityRepository;
        this.shoppingCartUpdateProvider = shoppingCartUpdateProvider;
    }

    @BeforeEach
    public void setup() {
        if (!customersPersistenceProvider.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customersPersistenceProvider.add(
                    CustomerTestDataBuilder.existingCustomer().build()
            );
        }
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldUpdateItemPriceAndTotalAmount() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();

        Product product1 = ProductTestDataBuilder.aProduct().price(new Money("2000")).build();
        Product product2 = ProductTestDataBuilder.aProductAltRamMemory().price(new Money("200")).build();

        shoppingCart.addItem(product1, new Quantity(2));
        shoppingCart.addItem(product2, new Quantity(1));

        persistenceProvider.add(shoppingCart);

        ProductId productIdToUpdate = product1.id();
        Money newProduct1Price = new Money("1500");
        Money expectedNewItemTotalPrice = newProduct1Price.multiply(new Quantity(2));
        Money expectedNewCartTotalAmount = expectedNewItemTotalPrice.add(new Money("200"));

        shoppingCartUpdateProvider.adjustPrice(productIdToUpdate, newProduct1Price);

        ShoppingCart updatedShoppingCart = persistenceProvider.ofId(shoppingCart.id()).orElseThrow();

        Assertions.assertThat(updatedShoppingCart.totalAmount()).isEqualTo(expectedNewCartTotalAmount);
        Assertions.assertThat(updatedShoppingCart.totalItems()).isEqualTo(new Quantity(3));

        ShoppingCartItem item = updatedShoppingCart.findItem(productIdToUpdate);

        Assertions.assertThat(item.totalAmount()).isEqualTo(expectedNewItemTotalPrice);
        Assertions.assertThat(item.price()).isEqualTo(newProduct1Price);

    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldUpdateItemAvailability() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();

        Product product1 = ProductTestDataBuilder.aProduct()
                .price(new Money("2000"))
                .inStock(true).build();
        Product product2 = ProductTestDataBuilder.aProductAltRamMemory()
                .price(new Money("200"))
                .inStock(true).build();

        shoppingCart.addItem(product1, new Quantity(2));
        shoppingCart.addItem(product2, new Quantity(1));

        persistenceProvider.add(shoppingCart);

        var productIdToUpdate = product1.id();
        var productIdNotToUpdate = product2.id();

        shoppingCartUpdateProvider.changeAvailability(productIdToUpdate, false);

        ShoppingCart updatedShoppingCart = persistenceProvider.ofId(shoppingCart.id()).orElseThrow();

        ShoppingCartItem item = updatedShoppingCart.findItem(productIdToUpdate);

        Assertions.assertThat(item.isAvailable()).isFalse();

        ShoppingCartItem item2 = updatedShoppingCart.findItem(productIdNotToUpdate);

        Assertions.assertThat(item2.isAvailable()).isTrue();

    }
}
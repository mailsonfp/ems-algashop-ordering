package com.ems.algaworks.algashop.ordering.infrastructure.provider;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.ems.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.CustomerTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.ShoppingCartTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditConfig;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.provider.CustomersPersistenceProvider;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.provider.ShoppingCartsPersistenceProvider;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.repository.ShoppingCartPersistenceEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
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
class ShoppingCartsPersistenceProviderIT {

    private ShoppingCartsPersistenceProvider persistenceProvider;
    private CustomersPersistenceProvider customersPersistenceProvider;
    private ShoppingCartPersistenceEntityRepository entityRepository;

    @Autowired
    public ShoppingCartsPersistenceProviderIT(ShoppingCartsPersistenceProvider persistenceProvider,
                                              CustomersPersistenceProvider customersPersistenceProvider,
                                              ShoppingCartPersistenceEntityRepository entityRepository) {
        this.persistenceProvider = persistenceProvider;
        this.customersPersistenceProvider = customersPersistenceProvider;
        this.entityRepository = entityRepository;
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
    public void shouldAddAndFindShoppingCart() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        assertThat(shoppingCart.version()).isNull();

        persistenceProvider.add(shoppingCart);

        assertThat(shoppingCart.version()).isNotNull().isEqualTo(0L);

        ShoppingCart foundCart = persistenceProvider.ofId(shoppingCart.id()).orElseThrow();
        assertThat(foundCart).isNotNull();
        assertThat(foundCart.id()).isEqualTo(shoppingCart.id());
        assertThat(foundCart.totalItems().value()).isEqualTo(3);
    }

    @Test
    public void shouldRemoveShoppingCartById() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        persistenceProvider.add(shoppingCart);
        assertThat(persistenceProvider.exists(shoppingCart.id())).isTrue();

        persistenceProvider.remove(shoppingCart.id());

        assertThat(persistenceProvider.exists(shoppingCart.id())).isFalse();
        assertThat(entityRepository.findById(shoppingCart.id().value())).isEmpty();
    }

    @Test
    public void shouldRemoveShoppingCartByEntity() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        persistenceProvider.add(shoppingCart);
        assertThat(persistenceProvider.exists(shoppingCart.id())).isTrue();

        persistenceProvider.remove(shoppingCart);

        assertThat(persistenceProvider.exists(shoppingCart.id())).isFalse();
    }

    @Test
    public void shouldFindShoppingCartByCustomerId() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart()
                .customerId(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)
                .build();
        persistenceProvider.add(shoppingCart);

        ShoppingCart foundCart = persistenceProvider.ofCustomer(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID).orElseThrow();

        assertThat(foundCart).isNotNull();
        assertThat(foundCart.customerId()).isEqualTo(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID);
        assertThat(foundCart.id()).isEqualTo(shoppingCart.id());
    }

    @Test
    public void shouldCorrectlyCountShoppingCarts() {
        long initialCount = persistenceProvider.count();

        ShoppingCart cart1 = ShoppingCartTestDataBuilder.aShoppingCart().build();
        persistenceProvider.add(cart1);

        Customer otherCustomer = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customersPersistenceProvider.add(otherCustomer);

        ShoppingCart cart2 = ShoppingCartTestDataBuilder.aShoppingCart().customerId(otherCustomer.id()).build();
        persistenceProvider.add(cart2);

        long finalCount = persistenceProvider.count();

        assertThat(finalCount).isEqualTo(initialCount + 2);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void shouldAddAndFindWhenNoTransaction() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();

        persistenceProvider.add(shoppingCart);

        assertThatNoException().isThrownBy(() -> {
            ShoppingCart foundCart = persistenceProvider.ofId(shoppingCart.id()).orElseThrow();
            assertThat(foundCart).isNotNull();
        });
    }
}
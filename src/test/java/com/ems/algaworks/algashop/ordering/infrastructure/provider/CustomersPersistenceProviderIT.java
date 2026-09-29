package com.ems.algaworks.algashop.ordering.infrastructure.provider;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.CustomerTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Email;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditConfig;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.provider.CustomersPersistenceProvider;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(properties = "algashop.h2-console.enabled=false")
@Import({
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        SpringDataAuditConfig.class
})
@Transactional
class CustomersPersistenceProviderIT {

    private final CustomersPersistenceProvider persistenceProvider;
    private final CustomerPersistenceEntityRepository entityRepository;

    @Autowired
    public CustomersPersistenceProviderIT(CustomersPersistenceProvider persistenceProvider,
                                          CustomerPersistenceEntityRepository entityRepository) {
        this.persistenceProvider = persistenceProvider;
        this.entityRepository = entityRepository;
    }

    @Test
    void shouldUpdateAndKeepPersistenceEntityState() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        persistenceProvider.add(customer);

        CustomerPersistenceEntity persistenceEntity = entityRepository.findById(customer.id().value()).orElseThrow();
        Assertions.assertThat(persistenceEntity.getEmail()).isEqualTo("johndoe@email.com");
        Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();

        customer = persistenceProvider.ofId(customer.id()).orElseThrow();
        customer.changeEmail(new Email("john.updated@gmail.com"));
        persistenceProvider.add(customer);

        persistenceEntity = entityRepository.findById(customer.id().value()).orElseThrow();

        Assertions.assertThat(persistenceEntity.getEmail()).isEqualTo("john.updated@gmail.com");
        Assertions.assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(persistenceEntity.getLastModifiedByUserId()).isNotNull();
        Assertions.assertThat(customer.version()).isEqualTo(persistenceEntity.getVersion());
    }
}

package com.ems.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.ems.algaworks.algashop.ordering.domain.model.entity.data.CustomerTestDataBuilder;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditConfig;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "algashop.h2-console.enabled=false")
@Transactional
@Import({SpringDataAuditConfig.class, CustomerPersistenceEntityAssembler.class})
class CustomerPersistenceEntityRepositoryIT {

    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;
    private final CustomerPersistenceEntityAssembler assembler;

    @Autowired
    public CustomerPersistenceEntityRepositoryIT(
            CustomerPersistenceEntityRepository customerPersistenceEntityRepository,
            CustomerPersistenceEntityAssembler assembler) {
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
        this.assembler = assembler;
    }

    @Test
    void shouldPersist() {
        CustomerPersistenceEntity entity = existingCustomer();

        customerPersistenceEntityRepository.saveAndFlush(entity);
        Assertions.assertThat(customerPersistenceEntityRepository.existsById(entity.getId())).isTrue();

        CustomerPersistenceEntity savedEntity = customerPersistenceEntityRepository.findById(entity.getId()).orElseThrow();

        Assertions.assertThat(savedEntity.getEmail()).isEqualTo("johndoe@email.com");
        Assertions.assertThat(savedEntity.getAddress().getCity()).isEqualTo("York");
    }

    @Test
    void shouldCount() {
        long customersCount = customerPersistenceEntityRepository.count();
        Assertions.assertThat(customersCount).isZero();
    }

    @Test
    void shouldSetAuditingValues() {
        CustomerPersistenceEntity entity = customerPersistenceEntityRepository.saveAndFlush(existingCustomer());

        Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }

    private CustomerPersistenceEntity existingCustomer() {
        return assembler.fromDomain(CustomerTestDataBuilder.brandNewCustomer().build());
    }
}

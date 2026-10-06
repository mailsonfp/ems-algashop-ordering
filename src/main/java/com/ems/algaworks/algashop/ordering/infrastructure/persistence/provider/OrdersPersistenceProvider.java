package com.ems.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Order;
import com.ems.algaworks.algashop.ordering.domain.model.repository.Orders;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.Money;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.OrderId;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersPersistenceProvider implements Orders {

    private final OrderPersistenceEntityRepository persistenceRepository;
    private final OrderPersistenceEntityAssembler assembler;
    private final OrderPersistenceEntityDisassembler disassembler;
    private final EntityManager entityManager;

    @Override
    public Optional<Order> ofId(OrderId orderId) {
        Optional<OrderPersistenceEntity> persistenceEntity = persistenceRepository.findById(orderId.value().toLong());
        return persistenceEntity.map(disassembler::toDomainEntity);
    }

    @Override
    public boolean exists(OrderId orderId) {
        return persistenceRepository.existsById(orderId.value().toLong());
    }

    @Override
    @Transactional(readOnly = false)
    public void add(Order aggregateRoot) {
        long orderId = aggregateRoot.id().value().toLong();

        persistenceRepository.findById(orderId)
                .ifPresentOrElse(
                        (persistenceEntity) -> {
                            update(aggregateRoot, persistenceEntity);
                        },
                        ()-> {
                            insert(aggregateRoot);
                        }
                );
    }

    private void update(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        entityManager.detach(persistenceEntity);
        persistenceRepository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    private void insert(Order aggregateRoot) {
        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        persistenceRepository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    @Override
    public Long count() {
        return persistenceRepository.count();
    }

    @Override
    public List<Order> placedByCustomerInYear(CustomerId customerId, Year year) {
        List<OrderPersistenceEntity> persistenceEntities = persistenceRepository.findByCustomer_IdAndPlacedAtBetween(
                customerId.value(),
                year.atDay(1).atStartOfDay().atOffset(OffsetDateTime.now().getOffset()),
                year.atMonth(12).atEndOfMonth().atTime(23, 59, 59).atOffset(OffsetDateTime.now().getOffset())
        );

        return persistenceEntities.stream()
                .map(disassembler::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public long salesQuantityByCustomerInYear(CustomerId customerId, Year year) {
        return persistenceRepository.salesQuantityByCustomerInYear(customerId.value(), year.getValue());
    }

    @Override
    public Money totalSalesSoldForCustomer(CustomerId customerId) {
        BigDecimal total = persistenceRepository.totalSoldForCustomer(customerId.value());
        return Money.of(total);

    }

    @SneakyThrows
    private void updateVersion(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        Field version = aggregateRoot.getClass().getDeclaredField("version");
        version.setAccessible(true);
        ReflectionUtils.setField(version, aggregateRoot, persistenceEntity.getVersion());
        version.setAccessible(false);
    }
}

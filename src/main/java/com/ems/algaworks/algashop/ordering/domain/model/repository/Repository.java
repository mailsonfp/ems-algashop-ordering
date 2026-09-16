package com.ems.algaworks.algashop.ordering.domain.model.repository;

import com.ems.algaworks.algashop.ordering.domain.model.entity.AggregateRoot;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.CustomerId;

import java.util.Optional;

public interface Repository<T extends AggregateRoot<ID>, ID> {

    Optional<T> ofId(ID id);
    boolean exists(ID id);
    void add(T aggregateRoot);
    int count();
}

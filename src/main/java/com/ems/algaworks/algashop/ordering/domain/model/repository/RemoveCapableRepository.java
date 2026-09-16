package com.ems.algaworks.algashop.ordering.domain.model.repository;

import com.ems.algaworks.algashop.ordering.domain.model.entity.AggregateRoot;

import java.util.Set;

public interface RemoveCapableRepository<T extends AggregateRoot<ID>, ID> extends Repository<T, ID> {
    void remove(T entity);
    void remove(ID id);
}

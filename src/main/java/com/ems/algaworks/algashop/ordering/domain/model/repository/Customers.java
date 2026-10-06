package com.ems.algaworks.algashop.ordering.domain.model.repository;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Email;

import java.util.Optional;

public interface Customers extends Repository<Customer, CustomerId> {

    Optional<Customer> ofEmail(Email email);
    boolean isEmailUnique(Email email, CustomerId customerId);
}

package com.ems.algaworks.algashop.ordering.domain.model.repository;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Order;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.Money;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.OrderId;

import java.time.Year;
import java.util.List;

public interface Orders extends Repository<Order, OrderId> {
    List<Order> placedByCustomerInYear(CustomerId customerId, Year year);

    long salesQuantityByCustomerInYear(CustomerId customerId, Year year);
    Money totalSalesSoldForCustomer(CustomerId customerId);
}

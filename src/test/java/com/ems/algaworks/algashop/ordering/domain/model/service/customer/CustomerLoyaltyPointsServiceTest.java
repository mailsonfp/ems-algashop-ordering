package com.ems.algaworks.algashop.ordering.domain.model.service.customer;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.ems.algaworks.algashop.ordering.domain.model.entity.Order;
import com.ems.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.CustomerTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.OrderTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.ProductTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.LoyaltyPoints;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.Quantity;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerLoyaltyPointsServiceTest {
    CustomerLoyaltyPointsService customerLoyaltyPointsService
            = new CustomerLoyaltyPointsService();

    @Test
    public void givenValidCustomerAndOrder_WhenAddingPoints_ShouldAccumulate() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        customerLoyaltyPointsService.addPoints(customer, order);

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    public void givenValidCustomerAndOrderWithLowTotalAmount_WhenAddingPoints_ShouldNotAccumulate() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        Product product = ProductTestDataBuilder.aProductAltRamMemory().build();

        Order order = OrderTestDataBuilder.anOrder().withItems(false).status(OrderStatus.DRAFT).build();
        order.addItem(product, new Quantity(1));
        order.place();
        order.markAsPaid();
        order.markAsReady();

        customerLoyaltyPointsService.addPoints(customer, order);

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(0));
    }
}
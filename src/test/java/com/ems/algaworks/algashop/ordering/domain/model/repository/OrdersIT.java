package com.ems.algaworks.algashop.ordering.domain.model.repository;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Order;
import com.ems.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.CustomerTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.entity.data.OrderTestDataBuilder;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.order.OrderId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@SpringBootTest(properties = "algashop.h2-console.enabled=false")
@Transactional
class OrdersIT {
    private Orders orders;
    private Customers customers;

    @Autowired
    public OrdersIT(Orders orders, Customers customers) {
        this.orders = orders;
        this.customers = customers;
    }

    @BeforeEach
    public void setup() {
        if (!customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customers.add(
                    CustomerTestDataBuilder.existingCustomer().build()
            );
        }
    }

    @Test
    public void shouldPersistAndFind() {
        Order originalOrder = createAndSaveOrder(OrderStatus.DRAFT);
        OrderId orderId = originalOrder.id();

        Optional<Order> possibleOrder = orders.ofId(orderId);

        Assertions.assertThat(possibleOrder).isPresent();

        Order savedOrder = possibleOrder.get();

        Assertions.assertThat(savedOrder).satisfies(
                s -> Assertions.assertThat(s.id()).isEqualTo(orderId),
                s -> Assertions.assertThat(s.customerId()).isEqualTo(originalOrder.customerId()),
                s -> Assertions.assertThat(s.totalAmount()).isEqualTo(originalOrder.totalAmount()),
                s -> Assertions.assertThat(s.totalItems()).isEqualTo(originalOrder.totalItems()),
                s -> Assertions.assertThat(s.placedAt()).isEqualTo(originalOrder.placedAt()),
                s -> Assertions.assertThat(s.paidAt()).isEqualTo(originalOrder.paidAt()),
                s -> Assertions.assertThat(s.canceledAt()).isEqualTo(originalOrder.canceledAt()),
                s -> Assertions.assertThat(s.readyAt()).isEqualTo(originalOrder.readyAt()),
                s -> Assertions.assertThat(s.status()).isEqualTo(originalOrder.status()),
                s -> Assertions.assertThat(s.paymentMethod()).isEqualTo(originalOrder.paymentMethod())
        );
    }

    @Test
    public void shouldUpdateExistingOrder() {
        Order order = createAndSaveOrder(OrderStatus.PLACED);

        order = orders.ofId(order.id()).orElseThrow();
        order.markAsPaid();

        orders.add(order);

        order = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(order.isPaid()).isTrue();

    }

    @Test
    public void shouldNotAllowStaleUpdates() {
        Order order = createAndSaveOrder(OrderStatus.PLACED);

        Order orderT1 = orders.ofId(order.id()).orElseThrow();
        Order orderT2 = orders.ofId(order.id()).orElseThrow();

        orderT1.markAsPaid();
        orders.add(orderT1);

        orderT2.cancel();

        Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
                .isThrownBy(()-> orders.add(orderT2));

        Order savedOrder = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(savedOrder.canceledAt()).isNull();
        Assertions.assertThat(savedOrder.paidAt()).isNotNull();

    }

    @Test
    public void shouldCountExistingOrders() {
        Assertions.assertThat(orders.count()).isZero();

        createAndSaveOrder(OrderStatus.DRAFT);
        createAndSaveOrder(OrderStatus.DRAFT);

        Assertions.assertThat(orders.count()).isEqualTo(2L);
    }

    @Test
    public void shouldReturnIfOrderExists() {
        Order order = createAndSaveOrder(OrderStatus.DRAFT);

        Assertions.assertThat(orders.exists(order.id())).isTrue();
        Assertions.assertThat(orders.exists(new OrderId())).isFalse();

    }

    @Test
    public void shouldFindOrdersPlacedByCustomerInYear() {
        Order order1 = createAndSaveOrder(OrderStatus.PLACED);
        Order order2 = createAndSaveOrder(OrderStatus.PLACED);
        createAndSaveOrder(OrderStatus.DRAFT);
        createAndSaveOrder(OrderStatus.CANCELED);

        List<Order> ordersPlacedThisYear = orders.placedByCustomerInYear(order1.customerId(), Year.now());
        Assertions.assertThat(ordersPlacedThisYear)
                .isNotEmpty()
                .hasSize(2)
                .extracting(Order::id)
                .containsExactlyInAnyOrder(order1.id(), order2.id());

        List<Order> ordersPlacedLastYear = orders.placedByCustomerInYear(order1.customerId(), Year.now().minusYears(1));
        Assertions.assertThat(ordersPlacedLastYear).isEmpty();
    }

    @Test
    public void shouldReturnTotalSoldForCustomer() {
        Order paid = createAndSaveOrder(OrderStatus.PAID);
        createAndSaveOrder(OrderStatus.DRAFT);

        Assertions.assertThat(orders.totalSalesSoldForCustomer(paid.customerId()))
                .isEqualTo(paid.totalAmount());
    }

    @Test
    public void shouldReturnSalesQuantityByCustomerInYear() {
        Order paid = createAndSaveOrder(OrderStatus.PAID);
        createAndSaveOrder(OrderStatus.DRAFT);

        Assertions.assertThat(orders.salesQuantityByCustomerInYear(paid.customerId(), Year.now()))
                .isEqualTo(1L);
        Assertions.assertThat(orders.salesQuantityByCustomerInYear(paid.customerId(), Year.now().minusYears(1)))
                .isZero();
    }

    private Order createAndSaveOrder(OrderStatus status) {
        Order order = OrderTestDataBuilder.anOrder().status(status).build();
        orders.add(order);
        return order;
    }

}
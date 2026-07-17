package com.ems.algaworks.algashop.ordering.domain.entity;

import com.ems.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.ems.algaworks.algashop.ordering.domain.valueobject.Address;
import com.ems.algaworks.algashop.ordering.domain.valueobject.BirthDate;
import com.ems.algaworks.algashop.ordering.domain.valueobject.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.valueobject.Document;
import com.ems.algaworks.algashop.ordering.domain.valueobject.Email;
import com.ems.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.ems.algaworks.algashop.ordering.domain.valueobject.LoyaltyPoints;
import com.ems.algaworks.algashop.ordering.domain.valueobject.Phone;
import com.ems.algaworks.algashop.ordering.domain.valueobject.ZipCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTest {
    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Customer.brandNew()
                        .fullName(new FullName("John", "Doe"))
                        .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
                        .email(new Email("invalid"))
                        .phone(new Phone("478-256-2504"))
                        .document(new Document("255-08-0578"))
                        .promotionNotificationsAllowed(false)
                        .address(address())
                        .build());
    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException() {
        Customer customer = Customer.brandNew()
                .fullName(new FullName("John", "Doe"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
                .email(new Email("john.doe@gmail.com"))
                .phone(new Phone("478-256-2504"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(false)
                .address(address())
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("invalid")));
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
        Customer customer = Customer.brandNew()
                .fullName(new FullName("John", "Doe"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
                .email(new Email("john.doe@gmail.com"))
                .phone(new Phone("478-256-2504"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(false)
                .address(address())
                .build();

        customer.archive();

        Assertions.assertWith(customer,
                c -> Assertions.assertThat(c.fullName()).isEqualTo(new FullName("Anonymous","Anonymous")),
                c -> Assertions.assertThat(c.email()).isNotEqualTo(new Email("john.doe@gmail.com")),
                c -> Assertions.assertThat(c.phone()).isEqualTo(new Phone("000-000-0000")),
                c -> Assertions.assertThat(c.document()).isEqualTo(new Document("000-00-0000")),
                c -> Assertions.assertThat(c.birthDate()).isNull(),
                c -> Assertions.assertThat(c.isPromotionNotificationsAllowed()).isFalse(),
                c -> Assertions.assertThat(c.address()).isEqualTo(
                        Address.builder()
                                .street("Main Street, 123")
                                .complement(null)
                                .neighborhood("Downtown")
                                .number("Anonymized")
                                .city("Sao Paulo")
                                .state("SP")
                                .zipCode(new ZipCode("00000000"))
                                .build()
                )
        );

    }

    @Test
    void given_archivedCustomer_whenTryToUpdate_shouldGenerateException() {
        Customer customer = Customer.existing()
                .id(new CustomerId())
                .fullName(new FullName("Anonymous", "Anonymous"))
                .birthDate(null)
                .email(new Email("anonymous@anonymous.com"))
                .phone(new Phone("000-000-0000"))
                .document(new Document("000-00-0000"))
                .promotionNotificationsAllowed(false)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .archivedAt(OffsetDateTime.now())
                .loyaltyPoints(new LoyaltyPoints(10))
                .address(address())
                .build();

        Assertions.assertThat(customer).isNotNull();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(()-> customer.changeEmail(new Email("email@gmail.com")));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(()-> customer.changePhone(new Phone("123-123-1111")));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::disablePromotionNotifications);
    }

    @Test
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {
        Customer customer = Customer.brandNew()
                .fullName(new FullName("John", "Doe"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
                .email(new Email("john.doe@gmail.com"))
                .phone(new Phone("478-256-2504"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(false)
                .address(address())
                .build();

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        customer.addLoyaltyPoints(new LoyaltyPoints(20));

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {
        Customer customer = Customer.brandNew()
                .fullName(new FullName("John", "Doe"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
                .email(new Email("john.doe@gmail.com"))
                .phone(new Phone("478-256-2504"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(false)
                .address(address())
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltyPoints(0)));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltyPoints(-10)));
    }

    private Address address() {
        return Address.builder()
                .street("Main Street, 123")
                .complement("Apartment 45")
                .neighborhood("Downtown")
                .number("123")
                .city("Sao Paulo")
                .state("SP")
                .zipCode(new ZipCode("12345678"))
                .build();
    }
}

package com.ems.algaworks.algashop.ordering.domain.model.service.customer;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.ems.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Address;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.BirthDate;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Document;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Email;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.FullName;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Phone;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.ZipCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest(properties = "algashop.h2-console.enabled=false")
class CustomerRegistrationServiceIT {

    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    @Test
    public void shouldRegister() {
        Customer customer = customerRegistrationService.register(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("johndoe@email.com"),
                new Phone("478-256-2604"),
                new Document("255-08-0578"),
                true,
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("Yostfort")
                        .state("South Carolina")
                        .zipCode(new ZipCode("04321002"))
                        .complement("Apt. 901")
                        .build()
        );

        Assertions.assertThat(customer.fullName()).isEqualTo(new FullName("John", "Doe"));
        Assertions.assertThat(customer.email()).isEqualTo(new Email("johndoe@email.com"));
    }

}
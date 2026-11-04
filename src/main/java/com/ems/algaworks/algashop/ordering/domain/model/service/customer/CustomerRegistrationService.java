package com.ems.algaworks.algashop.ordering.domain.model.service.customer;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.ems.algaworks.algashop.ordering.domain.model.exception.CustomerEmailIsInUseException;
import com.ems.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Address;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.BirthDate;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Document;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Email;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.FullName;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Phone;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerRegistrationService {
    private final Customers customers;

    public Customer register(
            FullName fullName,
            BirthDate birthDate,
            Email email,
            Phone phone,
            Document document,
            Boolean promotionNotificationsAllowed,
            Address address
    ) {

        Customer customer = Customer.brandNew()
                .fullName(fullName)
                .birthDate(birthDate)
                .email(email)
                .phone(phone)
                .document(document)
                .promotionNotificationsAllowed(promotionNotificationsAllowed)
                .address(address)
                .build();

        verifyEmailUniqueness(customer.email(), customer.id());

        return customer;
    }

    public void changeEmail(Customer customer, Email newEmail) {
        verifyEmailUniqueness(newEmail, customer.id());
        customer.changeEmail(newEmail);
    }

    private void verifyEmailUniqueness(Email email, CustomerId customerId) {
        if (!customers.isEmailUnique(email, customerId)) {
            throw new CustomerEmailIsInUseException();
        }
    }
}

package com.ems.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.ems.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Address;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.BirthDate;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.CustomerId;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Document;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Email;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.FullName;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.LoyaltyPoints;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.Phone;
import com.ems.algaworks.algashop.ordering.domain.model.valueobject.customer.ZipCode;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.ems.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerPersistenceEntityDisassembler {

    public Customer toDomainEntity(CustomerPersistenceEntity entity) {
        return Customer.existing()
                .id(new CustomerId(entity.getId()))
                .fullName(new FullName(entity.getFirstName(), entity.getLastName()))
                .birthDate(entity.getBirthDate() != null ? new BirthDate(entity.getBirthDate()) : null)
                .email(new Email(entity.getEmail()))
                .phone(new Phone(entity.getPhone()))
                .document(new Document(entity.getDocument()))
                .loyaltyPoints(new LoyaltyPoints(entity.getLoyaltyPoints()))
                .promotionNotificationsAllowed(entity.getPromotionNotificationsAllowed())
                .archived(entity.getArchived())
                .registeredAt(entity.getRegisteredAt())
                .archivedAt(entity.getArchivedAt())
                .address(toAddressValueObject(entity.getAddress()))
                .version(entity.getVersion())
                .build();
    }

    private Address toAddressValueObject(AddressEmbeddable address) {
        return Address.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(new ZipCode(address.getZipCode()))
                .build();
    }
}

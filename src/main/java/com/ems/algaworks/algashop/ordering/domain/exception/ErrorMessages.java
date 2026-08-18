package com.ems.algaworks.algashop.ordering.domain.exception;

public class ErrorMessages {
    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid";
    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";
    public static final String VALIDATION_ERROR_FULLNAME_IS_NULL = "FullName cannot be null";
    public static final String VALIDATION_ERROR_FULLNAME_IS_BLANK = "FullName cannot be blank";

    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer is archived it cannot be changed";

    public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGED = "Order %s status cannot be changed from %s to %s";
    public static final String ERROR_ORDER_DELIVERY_DATE_INVALID = "Order %s delivery date is invalid";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS = "Order %s cannot be closed because it has no items";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO
            = "Order %s cannot be placed, it has no shipping info";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO
            = "Order %s cannot be placed, it has no billing info";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_INVALID_SHIPPING_COST
            = "Order %s cannot be placed, it has no shipping cost";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_INVALID_DELIVERY_DATE
            = "Order %s cannot be placed, it has no delivery date";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD
            = "Order %s cannot be placed, it has no payment method";

    public static final String ERROR_ORDER_NOT_CONTAIN_ORDER_ITEM = "Order %s does not contain order item %s";
}

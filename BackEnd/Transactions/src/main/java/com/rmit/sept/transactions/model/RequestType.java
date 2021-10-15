package com.rmit.sept.transactions.model;

public enum RequestType {
    NEW_BUSINESS_USER, // pending registration
    NEW_BOOK_LISTING, // listing a new or used book for sale
    TO_BUSINESS_USER, // change from regular user to business user
    TO_REG_USER, // change from business user to regular user
    REFUND_TRANSACTION // refund a transaction
}

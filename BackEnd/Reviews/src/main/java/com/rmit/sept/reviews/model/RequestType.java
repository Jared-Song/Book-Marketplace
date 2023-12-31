package com.rmit.sept.reviews.model;

public enum RequestType {
    NEW_BUSINESS_USER, // pending registration
    NEW_BOOK_LISTING, // listing a new or used book for sale
    TO_BUSINESS_USER, // change from regular user to business user
    TO_REG_USER // change from business user to regular user
}

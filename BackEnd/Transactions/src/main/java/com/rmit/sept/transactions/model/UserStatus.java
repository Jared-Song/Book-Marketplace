package com.rmit.sept.transactions.model;

public enum UserStatus {
    ENABLED, // can login and access determines on role
    DISABLED, // can't login to account
    SUSPENDED, // for bad actions e.g. abuse or hateful comments/reviews
    PENDING_REGISTRATION, // can login and view catalogue but can't buy/sell
    DISABLED_REVIEWS_AND_REQUESTS // can't leave reviews or request anything (change to regular user or business user)
}

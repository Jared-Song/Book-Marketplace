package com.rmit.sept.requests.model;

public enum BookStatus {
    AVAILABLE, // available for buying, selling and viewing
    UNAVAILABLE, // unavailable for buying, selling but can be viewed
    PENDING_APPROVAL // unavailable for buying, selling or viewing
}

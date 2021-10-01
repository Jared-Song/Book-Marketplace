package com.rmit.sept.transactions.security;

public class SecurityConstant {

    public static final String USER_URLS = "/api/users/**";
    public static final String BOOK_URLS = "/api/books/**";
    public static final String TRANSACTION_URL = "/api/transactions/**";
    public static final String EDIT_USER_URLS = "/api/editUser/**";
    public static final String EDIT_BOOK_URLS = "/api/editBook/**";
    public static final String REQUEST_URLS = "/api/requests/**";
    public static final String BUSINESSUSER_URLS = "/api/business/**";
    public static final String BROWSING_URLS = "/api/browse/**";
    public static final String BOOK_REVIEWS_URLS = "/api/bookReview/**";
    public static final String USER_REVIEWS_URLS = "/api/userReview/**";
    public static final String H2_URL = "/h2-console/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 300_000; //30 seconds
}

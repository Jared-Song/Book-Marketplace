package com.rmit.sept.books.security;

public class SecurityConstant {

    public static final String BOOK_URLS = "/api/books/**";
    public static final String EDIT_BOOK_URLS = "/api/editBook/**";
    public static final String H2_URL = "/h2-console/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 300_000; //30 seconds
}

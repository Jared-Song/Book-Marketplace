package com.rmit.sept.users.security;

public class SecurityConstant {

    public static final String USER_URLS = "/api/users/**";
    public static final String EDIT_USER_URLS = "/api/editUser/**";
    public static final String BUSINESSUSER_URLS = "/api/business/**";
    public static final String H2_URL = "/h2-console/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 300_000; //30 seconds
}

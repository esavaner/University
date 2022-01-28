package com.example.calendarserver.security;

public class SecurityConstants {
	public static final String SECRET = "CalendarServerJWTGenerationSecretKey";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final long TOKEN_EXPIRATION_TIME = 8_640_000_000L; // 100 days
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String SIGN_IN_URL = "/user/login";
	public static final String USER_ID_HEADER = "UserID";
}

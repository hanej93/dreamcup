package com.dreamcup.config.jwt.config;

public interface JwtConfigProperties {

	public static final String SECRET = "ZWotand0LXNlY3JldC1lai1qd3Qtc2VjcmV0LWVqLWp3dC1zZWNyZXQtZWotand0LXNlY3JldC1lai1qd3Qtc2VjcmV0LWVqLWp3dC1zZWNyZXQtZWotand0LXNlY3JldA=="; // HS256 (대칭키)
	public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 일주일
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER = "Authorization";

}

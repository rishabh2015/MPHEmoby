package com.emoby.mph.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {
    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String FORBIDDEN = "ROLE_FORBIDDEN";
    public static final String HR = "ROLE_HR";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
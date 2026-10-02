package com.yigitaslan.call_center_system.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityConfigTest {
    private final SecurityConfig securityConfig = new SecurityConfig();

    @Test
    void userDetailsService_WhenCredentialsAreMissing_ShouldFailFast() {
        assertThrows(
                IllegalStateException.class,
                () -> securityConfig.userDetailsService(
                        securityConfig.passwordEncoder(),
                        "",
                        ""));
    }

    @Test
    void userDetailsService_WhenPasswordIsTooShort_ShouldFailFast() {
        assertThrows(
                IllegalStateException.class,
                () -> securityConfig.userDetailsService(
                        securityConfig.passwordEncoder(),
                        "api-user",
                        "short"));
    }

    @Test
    void userDetailsService_WhenCredentialsAreValid_ShouldStoreEncodedPassword() {
        String rawPassword = "strong-test-password";
        UserDetails user = securityConfig.userDetailsService(
                        securityConfig.passwordEncoder(),
                        "api-user",
                        rawPassword)
                .loadUserByUsername("api-user");

        assertNotEquals(rawPassword, user.getPassword());
        assertTrue(securityConfig.passwordEncoder().matches(rawPassword, user.getPassword()));
    }
}

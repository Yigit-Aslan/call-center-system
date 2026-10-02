package com.yigitaslan.call_center_system.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.web.csrf.CsrfToken;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

class CsrfControllerTest {
    private final CsrfController controller = new CsrfController();

    @Test
    void csrfToken_ShouldReturnCurrentRequestToken() {
        CsrfToken token = mock(CsrfToken.class);

        assertSame(token, controller.csrfToken(token));
    }
}

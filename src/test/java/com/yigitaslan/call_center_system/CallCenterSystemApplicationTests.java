package com.yigitaslan.call_center_system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
		"app.security.username=test-user",
		"app.security.password=test-password-not-used-outside-tests",
		"app.security.require-https=false",
		"server.ssl.enabled=false",
		"spring.datasource.url=jdbc:h2:mem:call-center-test;DB_CLOSE_DELAY=-1",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
@AutoConfigureMockMvc
class CallCenterSystemApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void csrfEndpoint_ShouldReturnTokenAndSetCookieForAuthenticatedClient() throws Exception {
		mockMvc.perform(get("/api/csrf").with(httpBasic("test-user", "test-password-not-used-outside-tests")))
				.andExpect(status().isOk())
				.andExpect(cookie().exists("XSRF-TOKEN"))
				.andExpect(jsonPath("$.token").isNotEmpty());
	}

	@Test
	void stateChangingRequests_WithoutCsrfToken_ShouldBeForbidden() throws Exception {
		var credentials = httpBasic("test-user", "test-password-not-used-outside-tests");

		mockMvc.perform(post("/api/customers")
						.with(credentials)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"firstName\":\"Ada\",\"lastName\":\"Lovelace\",\"email\":\"ada@example.com\"}"))
				.andExpect(status().isForbidden());
		mockMvc.perform(put("/api/customers/1")
						.with(credentials)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"firstName\":\"Ada\",\"lastName\":\"Lovelace\",\"email\":\"ada@example.com\"}"))
				.andExpect(status().isForbidden());
		mockMvc.perform(delete("/api/customers/1").with(credentials))
				.andExpect(status().isForbidden());
	}
}

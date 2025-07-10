package io.github.wingzero0.ssoserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(properties = "spring.application.version=0.12.1-SNAPSHOT-test")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class HomeControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testFetchVersion() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/version")
				.contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.version").value("0.12.1-SNAPSHOT-test"))
				.andDo(MockMvcResultHandlers.print());
	}
}

package controller;

import mag.config.SecurityConfig;
import mag.controller.HelloController;
import mag.controller.AuthController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest({ HelloController.class, AuthController.class })
@Import(SecurityConfig.class)
public class HelloControllerTests {

	@Autowired
	MockMvc mvc;

	@Test
	void rootWhenAuthenticatedThenSaysHelloUser() throws Exception {

		MvcResult result = this.mvc.perform(post("/token")
			.with(httpBasic("user", "password")))
			.andExpect(status().isOk())
			.andReturn();

		String token = result.getResponse().getContentAsString();

		this.mvc.perform(get("/")
			.header("Authorization", "Bearer " + token))
			.andExpect(content().string("Hello, user!"));

	}

	@Test
	void rootWhenUnauthenticatedThen401() throws Exception {

		this.mvc.perform(get("/"))
				.andExpect(status().isUnauthorized());

	}

	@Test
	void tokenWhenBadCredentialsThen401() throws Exception {

		this.mvc.perform(post("/token"))
				.andExpect(status().isUnauthorized());

	}

}

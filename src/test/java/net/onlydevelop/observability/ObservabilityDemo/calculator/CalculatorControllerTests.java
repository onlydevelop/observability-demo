package net.onlydevelop.observability.ObservabilityDemo.calculator;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void addsTwoNumbers() throws Exception {
		mockMvc.perform(get("/api/calculator/add").param("a", "2").param("b", "3"))
				.andExpect(status().isOk())
				.andExpect(content().string("5.0"));
	}

	@Test
	void subtractsTwoNumbers() throws Exception {
		mockMvc.perform(get("/api/calculator/subtract").param("a", "5").param("b", "3"))
				.andExpect(status().isOk())
				.andExpect(content().string("2.0"));
	}

	@Test
	void returnsServerErrorWhenFailureIsSimulated() {
		assertThatExceptionOfType(ServletException.class)
				.isThrownBy(() -> mockMvc.perform(
						get("/api/calculator/add").param("a", "2").param("b", "3").param("fail", "true")))
				.withCauseInstanceOf(IllegalStateException.class);
	}

	@Test
	void returnsBadRequestWhenParameterIsMissing() throws Exception {
		mockMvc.perform(get("/api/calculator/add").param("a", "2"))
				.andExpect(status().isBadRequest());
	}

}

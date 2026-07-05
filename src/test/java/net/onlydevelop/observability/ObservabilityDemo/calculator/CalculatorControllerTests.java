package net.onlydevelop.observability.ObservabilityDemo.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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

}

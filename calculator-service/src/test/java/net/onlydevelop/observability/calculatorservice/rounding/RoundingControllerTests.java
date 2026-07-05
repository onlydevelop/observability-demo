package net.onlydevelop.observability.calculatorservice.rounding;

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
class RoundingControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void roundsTwoNumbers() throws Exception {
		mockMvc.perform(get("/api/rounding/round").param("a", "3.14159").param("b", "2.71828").param("roundedUpto", "2"))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"a\":3.14,\"b\":2.72}"));
	}

	@Test
	void returnsBadRequestWhenParameterIsMissing() throws Exception {
		mockMvc.perform(get("/api/rounding/round").param("a", "2").param("b", "3"))
				.andExpect(status().isBadRequest());
	}

}

package net.onlydevelop.observability.calculatorservice.calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorServiceTests {

	private final CalculatorService calculatorService = new CalculatorService();

	@Test
	void addsTwoNumbers() {
		assertThat(calculatorService.add(2, 3)).isEqualTo(5);
	}

	@Test
	void subtractsTwoNumbers() {
		assertThat(calculatorService.subtract(5, 3)).isEqualTo(2);
	}

}

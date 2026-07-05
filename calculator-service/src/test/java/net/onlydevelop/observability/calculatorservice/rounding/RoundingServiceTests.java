package net.onlydevelop.observability.calculatorservice.rounding;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoundingServiceTests {

	private final RoundingService roundingService = new RoundingService();

	@Test
	void roundsToGivenNumberOfDigits() {
		assertThat(roundingService.round(3.14159, 2)).isEqualTo(3.14);
	}

	@Test
	void roundsToZeroDigits() {
		assertThat(roundingService.round(2.5, 0)).isEqualTo(3.0);
	}

	@Test
	void roundsUpHalfValues() {
		assertThat(roundingService.round(1.005, 2)).isEqualTo(1.01);
	}

}

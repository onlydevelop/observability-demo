package net.onlydevelop.observability.calculatorservice.calculator;

import net.onlydevelop.observability.calculatorservice.rounding.RoundedNumbers;
import net.onlydevelop.observability.calculatorservice.rounding.RoundingClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTests {

	@Mock
	private RoundingClient roundingClient;

	@Test
	void addsTwoNumbers() {
		when(roundingClient.round(anyDouble(), anyDouble(), anyInt())).thenReturn(new RoundedNumbers(2, 3));
		assertThat(new CalculatorService(roundingClient).add(2, 3)).isEqualTo(5);
	}

	@Test
	void subtractsTwoNumbers() {
		when(roundingClient.round(anyDouble(), anyDouble(), anyInt())).thenReturn(new RoundedNumbers(5, 3));
		assertThat(new CalculatorService(roundingClient).subtract(5, 3)).isEqualTo(2);
	}

	@Test
	void addsRoundedValuesReturnedByRoundingClient() {
		when(roundingClient.round(2.005, 3.004, 2)).thenReturn(new RoundedNumbers(2.01, 3.0));
		assertThat(new CalculatorService(roundingClient).add(2.005, 3.004)).isEqualTo(5.01);
	}

}

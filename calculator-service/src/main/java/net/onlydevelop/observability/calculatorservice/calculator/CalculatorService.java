package net.onlydevelop.observability.calculatorservice.calculator;

import net.onlydevelop.observability.calculatorservice.rounding.RoundedNumbers;
import net.onlydevelop.observability.calculatorservice.rounding.RoundingClient;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

	private static final int ROUNDING_DIGITS = 2;

	private final RoundingClient roundingClient;

	public CalculatorService(RoundingClient roundingClient) {
		this.roundingClient = roundingClient;
	}

	public double add(double a, double b) {
		RoundedNumbers rounded = roundingClient.round(a, b, ROUNDING_DIGITS);
		return rounded.a() + rounded.b();
	}

	public double subtract(double a, double b) {
		RoundedNumbers rounded = roundingClient.round(a, b, ROUNDING_DIGITS);
		return rounded.a() - rounded.b();
	}

}

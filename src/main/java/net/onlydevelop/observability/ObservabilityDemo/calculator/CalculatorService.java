package net.onlydevelop.observability.ObservabilityDemo.calculator;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

	public double add(double a, double b) {
		return a + b;
	}

}

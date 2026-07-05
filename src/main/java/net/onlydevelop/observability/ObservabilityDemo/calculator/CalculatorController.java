package net.onlydevelop.observability.ObservabilityDemo.calculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

	private final CalculatorService calculatorService;

	public CalculatorController(CalculatorService calculatorService) {
		this.calculatorService = calculatorService;
	}

	@GetMapping("/api/calculator/add")
	public double add(@RequestParam double a, @RequestParam double b, @RequestParam(defaultValue = "false") boolean fail) {
		simulateFailureIfRequested(fail);
		return calculatorService.add(a, b);
	}

	@GetMapping("/api/calculator/subtract")
	public double subtract(@RequestParam double a, @RequestParam double b, @RequestParam(defaultValue = "false") boolean fail) {
		simulateFailureIfRequested(fail);
		return calculatorService.subtract(a, b);
	}

	private void simulateFailureIfRequested(boolean fail) {
		if (fail) {
			throw new IllegalStateException("Simulated failure requested via fail=true");
		}
	}

}

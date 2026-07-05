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
	public double add(@RequestParam double a, @RequestParam double b) {
		return calculatorService.add(a, b);
	}

}

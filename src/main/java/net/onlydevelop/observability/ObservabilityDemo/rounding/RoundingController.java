package net.onlydevelop.observability.ObservabilityDemo.rounding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoundingController {

	private static final Logger log = LoggerFactory.getLogger(RoundingController.class);

	private final RoundingService roundingService;

	public RoundingController(RoundingService roundingService) {
		this.roundingService = roundingService;
	}

	@GetMapping("/api/rounding/round")
	public RoundedNumbers round(@RequestParam double a, @RequestParam double b, @RequestParam int roundedUpto) {
		log.info("Received round request a={} b={} roundedUpto={}", a, b, roundedUpto);
		return new RoundedNumbers(roundingService.round(a, roundedUpto), roundingService.round(b, roundedUpto));
	}

}

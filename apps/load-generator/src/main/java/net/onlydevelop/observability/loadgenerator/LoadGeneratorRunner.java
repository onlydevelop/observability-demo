package net.onlydevelop.observability.loadgenerator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadGeneratorRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(LoadGeneratorRunner.class);

	private static final String[] OPERATIONS = { "add", "subtract" };

	private final LoadGeneratorProperties properties;
	private final HttpClient httpClient = HttpClient.newHttpClient();

	public LoadGeneratorRunner(LoadGeneratorProperties properties) {
		this.properties = properties;
	}

	@Override
	public void run(String... args) throws InterruptedException {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		while (!Thread.currentThread().isInterrupted()) {
			double a = random.nextDouble(properties.getMinValue(), properties.getMaxValue());
			double b = random.nextDouble(properties.getMinValue(), properties.getMaxValue());
			String operation = OPERATIONS[random.nextInt(OPERATIONS.length)];

			callEndpoint(buildRequestPath(random, operation, a, b));

			long delayMs = random.nextLong(properties.getMinDelayMs(), properties.getMaxDelayMs() + 1);
			Thread.sleep(delayMs);
		}
	}

	private String buildRequestPath(ThreadLocalRandom random, String operation, double a, double b) {
		int roll = random.nextInt(100);
		if (roll < 80) {
			return "/api/calculator/" + operation + "?a=" + a + "&b=" + b;
		}
		if (roll < 87) {
			// missing required parameter -> 400
			return "/api/calculator/" + operation + "?a=" + a;
		}
		if (roll < 94) {
			// non-numeric parameter -> 400
			return "/api/calculator/" + operation + "?a=not-a-number&b=" + b;
		}
		if (roll < 97) {
			// unknown operation -> 404
			return "/api/calculator/multiply?a=" + a + "&b=" + b;
		}
		// forced server-side failure -> 500
		return "/api/calculator/" + operation + "?a=" + a + "&b=" + b + "&fail=true";
	}

	private void callEndpoint(String path) {
		URI uri = URI.create(properties.getTargetBaseUrl() + path);
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		log.info("Sending GET {}", uri);
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			log.info("GET {} -> status={} body={}", path, response.statusCode(), response.body());
		}
		catch (Exception ex) {
			log.warn("Failed to call calculator service at {}: {}", uri, ex.getMessage());
		}
	}

}

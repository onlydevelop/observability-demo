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

	private final LoadGeneratorProperties properties;
	private final HttpClient httpClient = HttpClient.newHttpClient();

	public LoadGeneratorRunner(LoadGeneratorProperties properties) {
		this.properties = properties;
	}

	private static final String[] OPERATIONS = { "add", "subtract" };

	@Override
	public void run(String... args) throws InterruptedException {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		while (!Thread.currentThread().isInterrupted()) {
			double a = random.nextDouble(properties.getMinValue(), properties.getMaxValue());
			double b = random.nextDouble(properties.getMinValue(), properties.getMaxValue());
			String operation = OPERATIONS[random.nextInt(OPERATIONS.length)];
			callCalculator(operation, a, b);

			long delayMs = random.nextLong(properties.getMinDelayMs(), properties.getMaxDelayMs() + 1);
			Thread.sleep(delayMs);
		}
	}

	private void callCalculator(String operation, double a, double b) {
		URI uri = URI.create(properties.getTargetBaseUrl() + "/api/calculator/" + operation + "?a=" + a + "&b=" + b);
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			log.info("{}({}, {}) -> status={} body={}", operation, a, b, response.statusCode(), response.body());
		}
		catch (Exception ex) {
			log.warn("Failed to call calculator service at {}: {}", uri, ex.getMessage());
		}
	}

}

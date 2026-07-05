package net.onlydevelop.observability.calculatorservice;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class RoundingClient {

	private final String baseUrl;
	private final HttpClient httpClient = HttpClient.newHttpClient();
	private final ObjectMapper objectMapper = new ObjectMapper();

	public RoundingClient(@Value("${rounding-service.base-url}") String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public RoundedNumbers round(double a, double b, int roundedUpto) {
		URI uri = URI.create(baseUrl + "/api/rounding/round?a=" + a + "&b=" + b + "&roundedUpto=" + roundedUpto);
		HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			return objectMapper.readValue(response.body(), RoundedNumbers.class);
		}
		catch (java.io.IOException | InterruptedException ex) {
			throw new IllegalStateException("Failed to call rounding-service at " + uri, ex);
		}
	}

}

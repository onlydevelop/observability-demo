package net.onlydevelop.observability.calculatorservice.rounding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RoundingClient {

	private final RestClient restClient;

	public RoundingClient(RestClient.Builder restClientBuilder, @Value("${rounding-service.base-url}") String baseUrl) {
		this.restClient = restClientBuilder.baseUrl(baseUrl).build();
	}

	public RoundedNumbers round(double a, double b, int roundedUpto) {
		return restClient.get()
				.uri(uriBuilder -> uriBuilder.path("/api/rounding/round")
						.queryParam("a", a)
						.queryParam("b", b)
						.queryParam("roundedUpto", roundedUpto)
						.build())
				.retrieve()
				.body(RoundedNumbers.class);
	}

}

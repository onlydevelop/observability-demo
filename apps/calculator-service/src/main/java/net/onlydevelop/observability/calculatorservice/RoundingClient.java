package net.onlydevelop.observability.calculatorservice;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class RoundingClient {

	private final String baseUrl;
	private final HttpClient httpClient = HttpClient.newHttpClient();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final OpenTelemetry openTelemetry;
	private final Tracer tracer;

	public RoundingClient(@Value("${rounding-service.base-url}") String baseUrl, OpenTelemetry openTelemetry) {
		this.baseUrl = baseUrl;
		this.openTelemetry = openTelemetry;
		this.tracer = openTelemetry.getTracer("calculator-service");
	}

	public RoundedNumbers round(double a, double b, int roundedUpto) {
		URI uri = URI.create(baseUrl + "/api/rounding/round?a=" + a + "&b=" + b + "&roundedUpto=" + roundedUpto);
		Span span = tracer.spanBuilder("GET /api/rounding/round").setSpanKind(SpanKind.CLIENT).startSpan();
		try (Scope scope = span.makeCurrent()) {
			HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(uri).GET();
			openTelemetry.getPropagators()
					.getTextMapPropagator()
					.inject(Context.current(), requestBuilder, HttpRequest.Builder::setHeader);
			HttpResponse<String> response = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
			return objectMapper.readValue(response.body(), RoundedNumbers.class);
		}
		catch (IOException | InterruptedException ex) {
			span.recordException(ex);
			throw new IllegalStateException("Failed to call rounding-service at " + uri, ex);
		}
		finally {
			span.end();
		}
	}

}

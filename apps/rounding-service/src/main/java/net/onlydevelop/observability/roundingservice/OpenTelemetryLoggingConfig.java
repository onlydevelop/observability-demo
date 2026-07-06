package net.onlydevelop.observability.roundingservice;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenTelemetryLoggingConfig {

	private final OpenTelemetry openTelemetry;

	OpenTelemetryLoggingConfig(OpenTelemetry openTelemetry) {
		this.openTelemetry = openTelemetry;
	}

	@PostConstruct
	void installLogbackAppender() {
		OpenTelemetryAppender.install(openTelemetry);
	}

}

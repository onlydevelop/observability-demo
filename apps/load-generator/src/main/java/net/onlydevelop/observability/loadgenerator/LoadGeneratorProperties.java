package net.onlydevelop.observability.loadgenerator;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "load-generator")
public class LoadGeneratorProperties {

	private String targetBaseUrl = "http://localhost:8080";
	private long minDelayMs = 200;
	private long maxDelayMs = 3000;
	private double minValue = 0;
	private double maxValue = 1000;

	public String getTargetBaseUrl() {
		return targetBaseUrl;
	}

	public void setTargetBaseUrl(String targetBaseUrl) {
		this.targetBaseUrl = targetBaseUrl;
	}

	public long getMinDelayMs() {
		return minDelayMs;
	}

	public void setMinDelayMs(long minDelayMs) {
		this.minDelayMs = minDelayMs;
	}

	public long getMaxDelayMs() {
		return maxDelayMs;
	}

	public void setMaxDelayMs(long maxDelayMs) {
		this.maxDelayMs = maxDelayMs;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

}

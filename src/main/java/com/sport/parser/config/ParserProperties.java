package com.sport.parser.config;

import java.util.Set;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.parser")
public class ParserProperties {

	private Api api;
	private Settings settings;
	private WebClientProperties webClient;

	@Data
	public static class Api {
		private String topLeagues;
		private String events;
		private String markets;
	}

	@Data
	public static class Settings {
		private Set<String> sports;
		private int threads;
	}

	@Data
	public static class WebClientProperties {
		private String maxJsonSize;
		private String mainUrl;
	}
}

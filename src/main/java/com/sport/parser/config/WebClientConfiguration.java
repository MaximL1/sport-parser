package com.sport.parser.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(ParserProperties.class)
public class WebClientConfiguration {

	@Bean
	public ExchangeStrategies exchangeStrategies(ParserProperties properties) {
		return ExchangeStrategies.builder()
								 .codecs(configurer ->
												 configurer.defaultCodecs()
														   .maxInMemorySize(Integer.parseInt(properties.getWebClient().getMaxJsonSize())))
								 .build();
	}

	@Bean
	public WebClient webClient(WebClient.Builder builder,
							   ExchangeStrategies exchangeStrategies,
							   ParserProperties properties) {
		return builder
				.baseUrl(properties.getWebClient().getMainUrl())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.exchangeStrategies(exchangeStrategies)
				.build();
	}
}


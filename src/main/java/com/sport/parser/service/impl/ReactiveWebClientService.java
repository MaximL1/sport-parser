package com.sport.parser.service.impl;

import com.sport.parser.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveWebClientService implements WebClientService {

	private static final String HTTP_ERROR_LOG = "HTTP error [{}] for URL: {}";

	private final WebClient webClient;

	private WebClient.ResponseSpec prepareRequest(String url) {
		return webClient.get()
						.uri(url)
						.retrieve()
						.onStatus(HttpStatusCode::isError, response -> {
							log.error(HTTP_ERROR_LOG, response.statusCode(), url);
							return response.createException();
						});
	}

	@Override
	public <T> Flux<T> client(String url, ParameterizedTypeReference<T> type) {
		return prepareRequest(url)
				.bodyToFlux(type)
				.doOnSubscribe(sub -> log.debug("Starting request (flux) to: {}", url))
				.doOnError(e -> log.error("Error during request (flux) to {}: {}", url, e.getMessage(), e));
	}

	@Override
	public <T> Flux<T> client(String url, Class<T> type) {
		return prepareRequest(url)
				.bodyToFlux(type)
				.doOnSubscribe(sub -> log.debug("Starting request (flux) to: {}", url))
				.doOnError(e -> log.error("Error during request (flux) to {}: {}", url, e.getMessage(), e));
	}

	@Override
	public <T> Mono<T> mono(String url, Class<T> type) {
		return prepareRequest(url)
				.bodyToMono(type)
				.doOnSubscribe(sub -> log.debug("Starting request (mono) to: {}", url))
				.doOnError(e -> log.error("Error during request (mono) to {}: {}", url, e.getMessage(), e));
	}
}





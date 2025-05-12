package com.sport.parser.service;

import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WebClientService {

	<T> Flux<T> client(String url, ParameterizedTypeReference<T> type);

	<T> Flux<T> client(String url, Class<T> type);

	<T> Mono<T> mono(String url, Class<T> type);
}

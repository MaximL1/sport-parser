package com.sport.parser.service.impl;

import java.util.Set;

import com.sport.parser.config.ParserProperties;
import com.sport.parser.data.Event;
import com.sport.parser.data.EventHolder;
import com.sport.parser.data.League;
import com.sport.parser.data.MarketHolder;
import com.sport.parser.data.Sport;
import com.sport.parser.data.SportEventView;
import com.sport.parser.service.ParserService;
import com.sport.parser.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {

	private final WebClientService webClientService;
	private final ParserProperties properties;

	@Override
	public void parseSportEvents() {
		Set<String> targetSports = properties.getSettings().getSports();

		webClientService.client(properties.getApi().getTopLeagues(), new ParameterizedTypeReference<Sport>() {})
						.filter(sport -> targetSports.contains(sport.name()))
						.flatMap(this::processSport)
						.parallel(properties.getSettings().getThreads())
						.runOn(Schedulers.parallel())
						.sequential()
						.doOnError(error -> log.error("Error during parsing", error))
						.subscribe(System.out::println);
	}

	private Flux<SportEventView> processSport(Sport sport) {
		return Flux.fromIterable(sport.regions())
				   .flatMap(region -> Flux.fromIterable(region.leagues())
										  .filter(League::top)
										  .take(2)
										  .flatMap(league -> fetchEvents(sport.name(), region.name(), league)));
	}

	private Flux<SportEventView> fetchEvents(String sportName, String regionName, League league) {
		String url = String.format(properties.getApi().getEvents(), league.id());

		return webClientService.mono(url, EventHolder.class)
							   .flatMapMany(holder -> Flux.fromIterable(holder.events()).take(2))
							   .flatMap(event -> fetchMarkets(sportName, regionName, league, event));
	}

	private Flux<SportEventView> fetchMarkets(String sport, String region, League league, Event event) {
		String url = String.format(properties.getApi().getMarkets(), event.id());

		return webClientService.client(url, MarketHolder.class)
							   .map(markets -> new SportEventView(
									   sport,
									   league.id(),
									   league.name(),
									   region,
									   event,
									   markets
							   ));
	}
}


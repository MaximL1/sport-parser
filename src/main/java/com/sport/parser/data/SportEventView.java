package com.sport.parser.data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record SportEventView(String sportName,
							 Long leagueNameId,
							 String leagueName,
							 String regionName,
							 Event event,
							 MarketHolder marketHolder) {

	private static final DateTimeFormatter FORMATTER =
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
	private static final String HEADER_FORMAT = "%s, %s %s%n";
	private static final String EVENT_FORMAT = "\t%s, %s UTC, %d%n";
	private static final String NO_MARKET = "\t\tNo available markets%n";
	private static final String MARKET_FORMAT = "\t\t%s%n";
	private static final String RUNNER_FORMAT = "\t\t\t%-15s, %5s, %20d%n";

	@Override
	public String toString() {
		return formatHeader() + formatEventLine() + formatMarkets();
	}

	private String formatHeader() {
		return String.format(HEADER_FORMAT, sportName, regionName, leagueName);
	}

	private String formatEventLine() {
		return String.format(EVENT_FORMAT,
							 event.name(),
							 FORMATTER.format(Instant.ofEpochMilli(event.kickoff())),
							 event.id());
	}

	private String formatMarkets() {
		if (marketHolder == null || marketHolder.combineMarkets().isEmpty()) {
			return NO_MARKET;
		}

		StringBuilder sb = new StringBuilder();
		marketHolder.combineMarkets().forEach((market, runners) ->
													  sb.append(formatMarket(market, runners))
		);
		return sb.toString();
	}

	private String formatMarket(String marketName, List<Runner> runners) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(MARKET_FORMAT, marketName));

		runners.stream()
			   .filter(Runner::open)
			   .map(this::formatRunner)
			   .forEach(sb::append);

		return sb.toString();
	}

	private String formatRunner(Runner runner) {
		return String.format(RUNNER_FORMAT,
							 runner.name(),
							 runner.priceStr(),
							 runner.id());
	}
}


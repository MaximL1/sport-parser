package com.sport.parser.data;

public record ParseContext (String sportName,
							String regionName,
							String leagueName,
							Long leagueId,
							Event event,
							MarketHolder marketHolder) {}

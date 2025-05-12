package com.sport.parser.data;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MarketHolder( Long id,
							String name,
							List<Market> markets) {

    public Map<String, List<Runner>> combineMarkets() {
		if (markets == null) {
			return Collections.emptyMap();
		}
		return markets.stream()
					  .collect(Collectors.groupingBy(
							  Market::name,
							  TreeMap::new,
							  Collectors.flatMapping(
									  market -> market.runners().stream(),
									  Collectors.toList()
							  )
					  ));
    }
}

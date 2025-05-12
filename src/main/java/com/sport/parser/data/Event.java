package com.sport.parser.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Event(Long id,
					String name,
					Long kickoff,
					Long lastUpdated) {}

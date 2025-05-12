package com.sport.parser.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Sport(Long id,
					String name,
					String family,
					List<Region> regions) {}

package com.sport.parser.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Region(Long id,
					 String name,
					 String family,
					 String url,
					 List<League> leagues) {}

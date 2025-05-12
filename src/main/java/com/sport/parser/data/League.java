package com.sport.parser.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record League(Long id,
					 String name,
					 String url,
					 Boolean top) {}

package com.sport.parser.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Runner(Long id,
					 String name,
					 Boolean open,
					 String priceStr) {}

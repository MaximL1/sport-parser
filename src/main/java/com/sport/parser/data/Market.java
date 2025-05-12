package com.sport.parser.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Market(Long id,
					 String name,
					 Boolean open,
					 List<Runner> runners) {}

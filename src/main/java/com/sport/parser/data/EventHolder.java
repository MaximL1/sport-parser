package com.sport.parser.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EventHolder(Integer totalCount,
						  String vtag,
						  List<Event> events) {}

package com.sport.parser.service.impl;

import com.sport.parser.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerService {

	private final ParserService parserService;

	@Scheduled(fixedDelayString = "${spring.scheduler.interval:30000}")
	public void schedulePolling() {
		parserService.parseSportEvents();
	}
}


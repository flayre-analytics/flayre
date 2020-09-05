package com.wbrawner.flayre.server.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Responsible for deleting events older than a given date
 */
@Component
public class EventScheduler {
    private static final Logger logger = LoggerFactory.getLogger(EventScheduler.class);
    private final EventRepository eventRepository;

    @Autowired
    public EventScheduler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteOldEvents() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DATE, -90);
        int deletedEvents = eventRepository.deleteEvents(calendar.getTime());
        logger.info("Deleted {} events older than 90 days", deletedEvents);
    }
}

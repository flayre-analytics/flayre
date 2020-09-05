package com.wbrawner.flayre.server.events;

import com.wbrawner.flayre.Event;
import com.wbrawner.flayre.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEvents() {
        return ResponseEntity.ok(eventRepository.getEvents());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @RequestHeader("User-Agent") String userAgent,
            @RequestBody EventRequest eventRequest
    ) {
        Event event = new Event(
                eventRequest.appId,
                eventRequest.date,
                eventRequest.type,
                userAgent,
                eventRequest.platform,
                eventRequest.manufacturer,
                eventRequest.model,
                eventRequest.version,
                eventRequest.locale,
                eventRequest.sessionId,
                eventRequest.data
        );
        if (eventRepository.saveEvent(event)) {
            return ResponseEntity.ok(event);
        }
        return ResponseEntity.notFound().build();
    }
}

package com.code.event_microservice.controllers;

import com.code.event_microservice.domain.Event;
import com.code.event_microservice.dto.EventRequestDto;
import com.code.event_microservice.dto.SubscriptionRequestDto;
import com.code.event_microservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    @PostMapping
    public Event createEvent(@RequestBody EventRequestDto event) {
        return eventService.createEvent(event);
    }

    @PostMapping("/{eventId}/register")
    public ResponseEntity<String> registerParticipant(@PathVariable String eventId, @RequestBody SubscriptionRequestDto subscriptionRequest) {
        eventService.registerParticipant(eventId, subscriptionRequest.participantEmail());
        return ResponseEntity.ok("Event created successfully");
    }
}

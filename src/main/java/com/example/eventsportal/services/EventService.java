package com.example.eventsportal.services;

import com.example.eventsportal.models.dtos.EventDto;
import com.example.eventsportal.models.entities.Event;

import java.util.Set;

public interface EventService {
    Set<Event> getAllEvents();

    EventDto findById(String id);
}

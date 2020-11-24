package com.example.eventsportal.services.impl;

import com.example.eventsportal.models.dtos.EventDto;
import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.repositories.EventRepository;
import com.example.eventsportal.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<Event> getAllEvents() {

        return new HashSet<>(this.eventRepository.findAll());

    }

    @Override
    public EventDto findById(String id) {

        Event event = this.eventRepository.findById(id).orElse(null);
        EventDto eventDto = this.modelMapper
                .map(event, EventDto.class);
        return eventDto;

    }
}

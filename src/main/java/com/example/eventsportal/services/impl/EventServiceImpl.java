package com.example.eventsportal.services.impl;

import com.example.eventsportal.models.bindingModels.EventBindingModel;
import com.example.eventsportal.models.dtos.EventDto;
import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.repositories.CategoryRepository;
import com.example.eventsportal.repositories.EventRepository;
import com.example.eventsportal.services.CategoryService;
import com.example.eventsportal.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public EventServiceImpl(EventRepository eventRepository, CategoryService categoryService, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<Event> getAllEvents() {

        Set<Event> events = new HashSet<>(this.eventRepository.findAll());
        return events;

    }

    @Override
    public EventDto findById(String id) {

        Event event = this.eventRepository.findById(id).orElse(null);
        EventDto eventDto = this.modelMapper
                .map(event, EventDto.class);
        return eventDto;

    }

    @Override
    public void buyTickets(String id) {
        Event event = this.eventRepository.findById(id)
                .orElse(null);

        event.setTicketsAvailable(event.getTicketsAvailable() - 1);

        this.eventRepository.saveAndFlush(event);
    }

    @Override
    public Event createEvent(EventBindingModel eventBindingModel) {

        Event event = this.modelMapper
                .map(eventBindingModel, Event.class);

        Category category = this.categoryService.findCategoryByName(eventBindingModel.getCategory());
        category.getEvents().add(event);

        this.eventRepository.saveAndFlush(event);
        this.categoryRepository.save(category);
        return event;
    }

}

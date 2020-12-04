package com.example.eventsportal.services.impl;

import com.example.eventsportal.models.dtos.EventDto;
import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.serviceModels.EventServiceModel;
import com.example.eventsportal.repositories.CategoryRepository;
import com.example.eventsportal.repositories.EventRepository;
import com.example.eventsportal.repositories.UserRepository;
import com.example.eventsportal.services.CategoryService;
import com.example.eventsportal.services.EventService;
import com.example.eventsportal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public EventServiceImpl(EventRepository eventRepository, CategoryService categoryService, CategoryRepository categoryRepository, UserService userService, UserRepository userRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.userRepository = userRepository;
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
    public void buyTickets(String id, String username) {
        Event event = this.eventRepository.findById(id)
                .orElse(null);

        User user = this.modelMapper
                .map(this.userService.findUserByUsername(username), User.class);

        if(this.eventRepository.findByUsers(user) != null) {
            event.setTicketsAvailable(event.getTicketsAvailable() - 1);
        }else {
            event.getUsers().add(user);
            event.setTicketsAvailable(event.getTicketsAvailable() - 1);
        }

        this.eventRepository.saveAndFlush(event);
    }

    @Override
    public Event createEvent(EventServiceModel eventServiceModel) {

        Event event = this.modelMapper
                .map(eventServiceModel, Event.class);

        Category category = this.categoryService.findCategoryByName(eventServiceModel.getCategory());
        category.getEvents().add(event);
        this.eventRepository.saveAndFlush(event);
        this.categoryRepository.save(category);
        return event;
    }

    @Override
    public void deleteEvent(String id) {

        Event event = this.eventRepository.findById(id)
                .orElse(null);
        Category category = this.categoryRepository.findByEvents(event);
        category.getEvents().remove(event);
        this.categoryRepository.saveAndFlush(category);
        this.eventRepository.deleteById(id);
    }

}

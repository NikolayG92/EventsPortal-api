package com.example.eventsportal.services.impl;

import com.example.eventsportal.exceptions.MoreTicketsTryingToBuyException;
import com.example.eventsportal.models.dtos.EventDto;
import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.entities.UserEventInfo;
import com.example.eventsportal.models.serviceModels.EventServiceModel;
import com.example.eventsportal.repositories.CategoryRepository;
import com.example.eventsportal.repositories.EventRepository;
import com.example.eventsportal.repositories.UserEventInfoRepository;
import com.example.eventsportal.repositories.UserRepository;
import com.example.eventsportal.services.CategoryService;
import com.example.eventsportal.services.EventService;
import com.example.eventsportal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final UserEventInfoRepository userEventInfoRepository;

    private final ModelMapper modelMapper;

    public EventServiceImpl(EventRepository eventRepository, CategoryService categoryService, CategoryRepository categoryRepository, UserService userService, UserRepository userRepository, UserEventInfoRepository userEventInfoRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userEventInfoRepository = userEventInfoRepository;
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String startDate = event.getStartedDate().format(formatter);

        eventDto.setStartDate(startDate);
        return eventDto;

    }

    @Override
    public Event buyTickets(String id, String username, int boughtTickets) throws MoreTicketsTryingToBuyException {
        Event event = this.eventRepository.findById(id)
                .orElse(null);

        if(boughtTickets > event.getTicketsAvailable()){
            throw new MoreTicketsTryingToBuyException("You cannot buy more tickets than the available!");
        }
        User user = this.modelMapper
                .map(this.userService.findUserByUsername(username), User.class);

        List<UserEventInfo> userEventInfos =
                this.userEventInfoRepository.
                findAllByUser(user);

        if(userEventInfos.size() == 0){
            userEventInfos = new ArrayList<>();
            userEventInfos.add(new UserEventInfo());
            userEventInfos.get(0).setUser(user);
            userEventInfos.get(0).setBoughtTickets(boughtTickets);
            this.userEventInfoRepository.saveAndFlush(userEventInfos.get(0));
            event.getUsers().add(userEventInfos.get(0));
            event.setTicketsAvailable(event.getTicketsAvailable() - boughtTickets);
            this.eventRepository.save(event);
        }else {
            if(event.getUsers().size() > 0){
                event.getUsers().forEach(userEventInfo -> {
                    if(userEventInfo.getUser().equals(user)){
                        userEventInfo.setBoughtTickets(userEventInfo.getBoughtTickets() + boughtTickets);
                        event.setTicketsAvailable(event.getTicketsAvailable() - boughtTickets);
                        this.userEventInfoRepository.saveAndFlush(userEventInfo);
                        this.eventRepository.saveAndFlush(event);

                    }else {
                        UserEventInfo eventInfo = new UserEventInfo();
                        eventInfo.setUser(user);
                        eventInfo.setBoughtTickets(boughtTickets);
                        this.userEventInfoRepository.saveAndFlush(eventInfo);
                        event.getUsers().add(eventInfo);

                        event.setTicketsAvailable(event.getTicketsAvailable() - boughtTickets);
                        this.eventRepository.saveAndFlush(event);
                    }
                });
            }else {
                userEventInfos.add(new UserEventInfo());
                userEventInfos.get(userEventInfos.size()-1).setUser(user);
                userEventInfos.get(userEventInfos.size()-1).setBoughtTickets(boughtTickets);
                this.userEventInfoRepository.saveAndFlush(userEventInfos.get(userEventInfos.size()-1));
                event.getUsers().add(userEventInfos.get(userEventInfos.size()-1));
                event.setTicketsAvailable(event.getTicketsAvailable() - boughtTickets);
                this.eventRepository.save(event);
            }


        }
         return event;
    }

    @Override
    public Event createEvent(EventServiceModel eventServiceModel) {

        Event event = this.modelMapper
                .map(eventServiceModel, Event.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(eventServiceModel.getStartDate().substring(0, 10), formatter);
        event.setStartedDate(localDate);
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

        if (event != null) {
            event.getUsers().forEach(userEventInfo -> {
                this.userEventInfoRepository.delete(userEventInfo);
            });
        }
        this.categoryRepository.saveAndFlush(category);
        this.eventRepository.deleteById(id);
    }

    @Override
    public Set<Event> getEventsByUser(String name) {

        User user = this.userService.findUserByUsername(name);
        List<UserEventInfo> userEventInfos = this.userEventInfoRepository
                .findAllByUser(user);
        Set<Event> events = new HashSet<>();
        userEventInfos.forEach(userEventInfo ->
                events.add(eventRepository.findByUsers(userEventInfo)));

        return events;
    }

}

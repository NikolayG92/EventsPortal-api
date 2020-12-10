package com.example.eventsportal.services;

import com.example.eventsportal.exceptions.MoreTicketsTryingToBuyException;
import com.example.eventsportal.models.bindingModels.EventBindingModel;
import com.example.eventsportal.models.dtos.EventDto;
import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.models.serviceModels.EventServiceModel;
import com.example.eventsportal.models.views.EventViewModel;

import java.util.List;
import java.util.Set;

public interface EventService {
    Set<Event> getAllEvents();

    EventDto findById(String id);

    Event buyTickets(String id, String username, int boughtTickets) throws MoreTicketsTryingToBuyException;

    Event createEvent(EventServiceModel eventServiceModel);

    void deleteEvent(String id);

    Set<Event> getEventsByUser(String name);
}

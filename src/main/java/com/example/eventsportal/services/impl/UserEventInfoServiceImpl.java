package com.example.eventsportal.services.impl;

import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.entities.UserEventInfo;
import com.example.eventsportal.repositories.UserEventInfoRepository;
import com.example.eventsportal.services.EventService;
import com.example.eventsportal.services.UserEventInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserEventInfoServiceImpl implements UserEventInfoService {

    private final UserEventInfoRepository userEventInfoRepository;
    private final EventService eventService;

    public UserEventInfoServiceImpl(UserEventInfoRepository userEventInfoRepository, EventService eventService) {
        this.userEventInfoRepository = userEventInfoRepository;
        this.eventService = eventService;
    }

    @Override
    public List<UserEventInfo> findAllByUser(User user) {

        return this.userEventInfoRepository.findAllByUser(user);
    }
}

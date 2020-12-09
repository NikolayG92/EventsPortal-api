package com.example.eventsportal.services;

import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.entities.UserEventInfo;

import java.util.List;
import java.util.Set;

public interface UserEventInfoService {
    List<UserEventInfo> findAllByUser(User user);

}

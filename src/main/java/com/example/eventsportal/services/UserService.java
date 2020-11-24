package com.example.eventsportal.services;

import com.example.eventsportal.models.dtos.UserDto;
import com.example.eventsportal.models.entities.User;

import java.util.Set;

public interface UserService {

    Set<User> getAllUsers();


    UserDto findUserById(String id);
}

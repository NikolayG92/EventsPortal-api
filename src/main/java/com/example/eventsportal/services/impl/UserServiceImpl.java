package com.example.eventsportal.services.impl;

import com.example.eventsportal.models.dtos.UserDto;
import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.repositories.UserRepository;
import com.example.eventsportal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Set<User> getAllUsers() {
        return new HashSet<>(this.userRepository.findAll());
    }

    @Override
    public UserDto findUserById(String id) {
        User user = this.userRepository.findById(id).orElse(null);
        UserDto userDto = this.modelMapper
                .map(user, UserDto.class);
        return userDto;
    }
}

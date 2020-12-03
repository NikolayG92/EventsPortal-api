package com.example.eventsportal.services;

import com.example.eventsportal.models.bindingModels.LoginBindingModel;
import com.example.eventsportal.models.bindingModels.UserRegisterBindingModel;
import com.example.eventsportal.models.dtos.UserDto;
import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.views.LoginViewModel;
import com.example.eventsportal.models.views.RegisterViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

public interface UserService {

    Set<User> getAllUsers();


    UserDto findUserById(String id);

    RegisterViewModel register(UserRegisterBindingModel userRegisterBindingModel);


    User findUserByUsername(String username);
}

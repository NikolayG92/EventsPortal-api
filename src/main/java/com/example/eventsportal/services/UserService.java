package com.example.eventsportal.services;

import com.example.eventsportal.models.bindingModels.LoginBindingModel;
import com.example.eventsportal.models.bindingModels.UserEditBindingModel;
import com.example.eventsportal.models.bindingModels.UserRegisterBindingModel;
import com.example.eventsportal.models.dtos.UserDto;
import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.serviceModels.UserServiceModel;
import com.example.eventsportal.models.views.LoginViewModel;
import com.example.eventsportal.models.views.RegisterViewModel;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {

    Set<User> getAllUsers();


    UserDto findUserById(String id);


    User findUserByUsername(String username);

    RegisterViewModel signUpUser(UserRegisterBindingModel userRegisterBindingModel);

    LoginViewModel signInUser(LoginBindingModel loginBindingModel);

    User editUser(UserEditBindingModel userEditBindingModel,
                  UserServiceModel userServiceModel, String username);
}

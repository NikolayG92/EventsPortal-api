package com.example.eventsportal.services.impl;

import com.example.eventsportal.config.jwt.JwtUtils;
import com.example.eventsportal.models.bindingModels.LoginBindingModel;
import com.example.eventsportal.models.bindingModels.UserEditBindingModel;
import com.example.eventsportal.models.bindingModels.UserRegisterBindingModel;
import com.example.eventsportal.models.dtos.UserDto;
import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.serviceModels.UserServiceModel;
import com.example.eventsportal.models.views.LoginViewModel;
import com.example.eventsportal.models.views.RegisterViewModel;
import com.example.eventsportal.repositories.RoleRepository;
import com.example.eventsportal.repositories.UserRepository;
import com.example.eventsportal.services.RoleService;
import com.example.eventsportal.services.UserService;

import com.google.common.collect.Sets;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        this.roleService = roleService;
        this.jwtUtils = jwtUtils;

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

    @Override
    public LoginViewModel signInUser(LoginBindingModel loginBindingModel) {

        final User user = findUserByUsername(loginBindingModel.getUsername());

        if(bCryptPasswordEncoder.matches(loginBindingModel.getPassword(), user.getPassword())){
            final String token = jwtUtils.generateToken(user);

            return new LoginViewModel(token);
        }else {
            throw new SecurityException("Wrong credentials!");
        }

    }

    @Override
    public User editUser(UserEditBindingModel userEditBindingModel, UserServiceModel userServiceModel, String username) {
        User user = this.modelMapper
        .map(this.userRepository.findByUsername(username)
                .orElse(null), User.class);

        if (bCryptPasswordEncoder.matches(userEditBindingModel.getOldPassword(), user.getPassword()) &&
        userEditBindingModel.getNewPassword().equals(userEditBindingModel.getConfirmPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userEditBindingModel.getNewPassword()));
            user.setImageUrl(userServiceModel.getImageUrl());

            this.userRepository.saveAndFlush(user);
            return user;

        }else {
            throw new SecurityException("Wrong credentials!");
        }

    }

    @Override
    public User findUserByUsername(String username) {
        User user = (User) this.userRepository.findByUsername(username)
                .orElse(null);

        return user;

    }

    @Override
    @Transactional
    public RegisterViewModel signUpUser(UserRegisterBindingModel userRegisterBindingModel) {

        if(!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            throw new SecurityException("Passwords do not match!");
        }
        this.userRepository.findByUsername(userRegisterBindingModel.getUsername()).ifPresent(u -> {
            throw new EntityExistsException(String.format("User with username '%s' already exists.", userRegisterBindingModel.getUsername()));
        });

        User user = this.modelMapper.map(userRegisterBindingModel, User.class);

        if (this.userRepository.count() == 0) {
            roleService.seedRolesInDb();

            user.setAuthorities(Sets.newHashSet(this.roleService.findByAuthority("ADMIN")));
        } else {
            user.setAuthorities(Sets.newHashSet(this.roleService.findByAuthority("USER")));
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));



        this.userRepository.saveAndFlush(user);

        return this.modelMapper.map(user, RegisterViewModel.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Username %s not found", username)));
    }
}

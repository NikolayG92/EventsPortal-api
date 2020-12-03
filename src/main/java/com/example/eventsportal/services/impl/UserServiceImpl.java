package com.example.eventsportal.services.impl;

import com.example.eventsportal.models.bindingModels.LoginBindingModel;
import com.example.eventsportal.models.bindingModels.UserRegisterBindingModel;
import com.example.eventsportal.models.dtos.UserDto;
import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.views.LoginViewModel;
import com.example.eventsportal.models.views.RegisterViewModel;
import com.example.eventsportal.repositories.RoleRepository;
import com.example.eventsportal.repositories.UserRepository;
import com.example.eventsportal.services.RoleService;
import com.example.eventsportal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;


    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;

        this.authenticationManager = authenticationManager;
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
    public RegisterViewModel register(UserRegisterBindingModel userRegisterBindingModel) {


        this.userRepository.findByUsername(userRegisterBindingModel.getUsername()).ifPresent(u -> {
            throw new EntityExistsException(String.format("User with username '%s' already exists.", userRegisterBindingModel.getUsername()));
        });

        User user = this.modelMapper.map(userRegisterBindingModel, User.class);


        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setAuthorities(new HashSet<>());
        user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));

        this.userRepository.saveAndFlush(user);

        return this.modelMapper.map(user, RegisterViewModel.class);
    }


    @Override
    public User findUserByUsername(String username) {
        User user = (User) this.userRepository.findByUsername(username)
                .orElse(null);

        return user;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow
                        (() ->
                                new UsernameNotFoundException(String.format("%s user not found", username)));
    }
}

package com.example.eventsportal.init;

import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.repositories.EventRepository;
import com.example.eventsportal.repositories.UserRepository;
import com.example.eventsportal.services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UsersInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersInit(UserRepository userRepository, EventRepository eventRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;

        this.roleService = roleService;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {


        if(userRepository.count() == 0){
            this.roleService.seedRolesInDb();
            User user = new User();
            user.setEmail("test@abv.bg");
            user.setEvents(new ArrayList<>());
            user.getEvents().add(this.eventRepository.findAll().get(0));
            user.setPassword(bCryptPasswordEncoder.encode("12345"));
            user.setUsername("test1");
            user.setAuthorities(this.roleService.findAllRoles());
            User user1 = new User();
            user1.setEmail("test1@abv.bg");
            user1.setEvents(new ArrayList<>());
            user1.getEvents().add(this.eventRepository.findAll().get(0));
            user1.setPassword(bCryptPasswordEncoder.encode("12345"));
            user1.setUsername("test2");
            user1.setAuthorities(this.roleService.findAllRoles());
            this.userRepository.save(user);
            this.userRepository.save(user1);
        }
    }
}

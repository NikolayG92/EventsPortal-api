package com.example.eventsportal.init;

import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.repositories.EventRepository;
import com.example.eventsportal.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UsersInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public UsersInit(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.count() == 0){
            User user = new User();
            user.setEmail("test@abv.bg");
            user.setEvents(new ArrayList<>());
            user.getEvents().add(this.eventRepository.findAll().get(0));
            user.setPassword("1234");
            user.setUsername("test1");
            this.userRepository.save(user);
            User user1 = new User();
            user.setEmail("test1@abv.bg");
            user.setEvents(new ArrayList<>());
            user.getEvents().add(this.eventRepository.findAll().get(0));
            user.setPassword("1234");
            user.setUsername("test2");
            this.userRepository.save(user);
            this.userRepository.save(user1);
        }
    }
}

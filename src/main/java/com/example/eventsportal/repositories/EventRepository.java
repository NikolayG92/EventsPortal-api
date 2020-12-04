package com.example.eventsportal.repositories;

import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    Event findByUsers(User user);

}

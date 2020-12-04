package com.example.eventsportal.repositories;

import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Category findByName(String name);

    Category findByEvents(Event event);

}

package com.example.eventsportal.init;

import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.repositories.CategoryRepository;
import com.example.eventsportal.repositories.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EventsInitialization implements CommandLineRunner {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;


    public EventsInitialization(EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(eventRepository.count() == 0) {
            Event event = new Event();
            Category category = new Category();
            category.setName("Sport");

            this.categoryRepository.save(category);
            event.setCategory(category);
            event.setDescription("Sofia open");
            event.setName("Sofia Open");
            event.setTicketsAvailable(300);
            event.setImageUrl("src\\main\\resources\\static\\rafael-nadal-us-open.png");


            Event event1 = new Event();
            event1.setCategory(category);
            event1.setDescription("Test 2");
            event1.setTicketsAvailable(25);

            this.eventRepository.save(event);
            this.eventRepository.save(event1);
        }
    }
}

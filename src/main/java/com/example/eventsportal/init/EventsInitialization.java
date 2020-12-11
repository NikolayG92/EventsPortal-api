package com.example.eventsportal.init;

import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.repositories.CategoryRepository;
import com.example.eventsportal.repositories.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            Category categoryTheatre = new Category();
            categoryTheatre.setName("Theatre");


            event.setDescription("Sofia open tournament is ATP 250");
            event.setName("Sofia Open");
            event.setTicketsAvailable(300);
            event.setImageUrl("https://tennis.bg/uploaded/posts/d800003064880a840d6e8319d2fee200.jpg");

            event.setStartedDate(LocalDate.of(2020, 12, 31));

            Event event1 = new Event();
            event1.setName("Us Open");
            event1.setDescription("Us Open tournament starts at 17 august");
            event1.setTicketsAvailable(25);
            event1.setImageUrl("https://www.insidesport.co/wp-content/uploads/2020/08/20200820_064609.jpg");
            event1.setStartedDate(LocalDate.of(2020, 12, 31));

            Event theatre = new Event();
            theatre.setName("Romeo And Juliet");
            theatre.setTicketsAvailable(1050);
            theatre.setDescription("This one is our theathre Lorem Ipsum hahahaa uaewprua fasdfasl;d fjasdf as");
            theatre.setStartedDate(LocalDate.of(2020, 12, 31));
            theatre.setImageUrl("https://www.theoldglobe.org/link/9814276cefdb459c9d5138b0627caed5.aspx?id=36498");
            this.eventRepository.save(event);
            this.eventRepository.save(event1);
            this.eventRepository.save(theatre);

            category.setEvents(new ArrayList<>());
            category.getEvents().add(event);
            category.getEvents().add(event1);
            categoryTheatre.setEvents(new ArrayList<>());
            categoryTheatre.getEvents().add(theatre);
            this.categoryRepository.save(categoryTheatre);

            this.categoryRepository.save(category);
        }
    }
}

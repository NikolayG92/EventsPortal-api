package com.example.eventsportal.api;

import com.example.eventsportal.models.dtos.EventDto;
import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Event>> getAllEvents(){

        return ResponseEntity.ok()
                .body(this.eventService
                .getAllEvents());
    }

    @GetMapping("/")
    public ResponseEntity<EventDto> findById(@RequestParam("id") String id){

        EventDto event = this.eventService
                .findById(id);

        if(event != null) {
            return ResponseEntity
                    .ok()
                    .body(event);
        }else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}

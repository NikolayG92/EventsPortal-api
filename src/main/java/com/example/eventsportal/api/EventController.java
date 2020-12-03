package com.example.eventsportal.api;

import com.example.eventsportal.models.bindingModels.EventBindingModel;
import com.example.eventsportal.models.views.EventViewModel;
import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    private final EventService eventService;
    private final ModelMapper modelMapper;

    public EventController(EventService eventService, ModelMapper modelMapper) {
        this.eventService = eventService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Event>> getAllEvents(){

        return ResponseEntity.ok()
                .body(this.eventService
                .getAllEvents());

    }

    @GetMapping("/{id}")
    public ResponseEntity<EventViewModel> findById(@PathVariable("id") String id){

        return ResponseEntity
                .ok()
                .body(this.modelMapper.map(this.eventService.findById(id), EventViewModel.class));
    }

    @PostMapping("/buyTickets/{id}")
    public ResponseEntity<Event> buyTickets(@PathVariable("id") String id){
        this.eventService.buyTickets(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventBindingModel eventBindingModel){

        return ResponseEntity
                .ok()
                .body(this.eventService.createEvent(eventBindingModel));

    }

}

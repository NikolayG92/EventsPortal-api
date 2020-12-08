package com.example.eventsportal.api;

import com.example.eventsportal.models.bindingModels.EventBindingModel;
import com.example.eventsportal.models.serviceModels.EventServiceModel;
import com.example.eventsportal.models.views.EventViewModel;
import com.example.eventsportal.models.entities.Event;
import com.example.eventsportal.services.CloudinaryService;
import com.example.eventsportal.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    private final EventService eventService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public EventController(EventService eventService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.eventService = eventService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Event>> getAllEvents(){

        return ResponseEntity.ok()
                .body(this.eventService
                .getAllEvents());

    }

    @GetMapping("/getEventsByUser")
    public ResponseEntity<Set<Event>> getAllEventByUser(Principal principal){

        return ResponseEntity.ok()
                .body(this.eventService.getEventsByUser(principal.getName()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<EventViewModel> findById(@PathVariable("id") String id){

        return ResponseEntity
                .ok()
                .body(this.modelMapper.map(this.eventService.findById(id), EventViewModel.class));
    }

    @PostMapping("/buyTickets/{id}")
    public ResponseEntity<Event> buyTickets(@PathVariable("id") String id,
                                            Principal principal){
        this.eventService.buyTickets(id, principal.getName());
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping(value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> createEvent(@RequestPart("event") @Valid EventBindingModel eventBindingModel,
                                             @RequestPart("file") @Valid MultipartFile file) throws IOException {

        EventServiceModel event = this.modelMapper
                .map(eventBindingModel, EventServiceModel.class);

        imgValidate(event, file);

        return ResponseEntity
                .ok()
                .body(this.eventService.createEvent(event));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") String id){

        this.eventService.deleteEvent(id);

        return ResponseEntity
                .ok()
                .build();
    }


    private void imgValidate(EventServiceModel eventServiceModel, MultipartFile file) throws IOException {
        if (file != null) {
            eventServiceModel.setImageUrl(cloudinaryService.uploadImg(file));
        } else {
            eventServiceModel.setImageUrl("/img/no-image-available.jpg");
        }
    }

}

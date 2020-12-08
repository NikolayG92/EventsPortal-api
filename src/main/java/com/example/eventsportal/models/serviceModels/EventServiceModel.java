package com.example.eventsportal.models.serviceModels;

import com.example.eventsportal.models.entities.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class EventServiceModel {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String startDate;
    private int ticketsAvailable;
    private String category;

}

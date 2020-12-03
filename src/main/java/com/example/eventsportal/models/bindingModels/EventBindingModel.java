package com.example.eventsportal.models.bindingModels;

import com.example.eventsportal.models.entities.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EventBindingModel {

    private String name;
    private String description;
    private int ticketsAvailable;
    private String imageUrl;
    private String category;
}

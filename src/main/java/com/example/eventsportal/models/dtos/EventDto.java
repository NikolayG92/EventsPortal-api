package com.example.eventsportal.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EventDto {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private int ticketsAvailable;
    private CategoryDto category;
}

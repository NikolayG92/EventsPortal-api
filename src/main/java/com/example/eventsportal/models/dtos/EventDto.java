package com.example.eventsportal.models.dtos;

import com.example.eventsportal.models.entities.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;

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

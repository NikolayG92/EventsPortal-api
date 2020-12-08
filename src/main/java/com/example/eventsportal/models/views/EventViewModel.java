package com.example.eventsportal.models.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EventViewModel {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String startDate;
    private int ticketsAvailable;
}

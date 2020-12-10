package com.example.eventsportal.models.bindingModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@NoArgsConstructor
@Getter
@Setter
public class EventBindingModel {

    private String name;
    private String description;
    private int ticketsAvailable;
    private int boughtTickets;
    private String startDate;
    private String category;
}

package com.example.eventsportal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
@Setter
public class Event extends BaseEntity{

    private String name;
    private String description;
    private String imageUrl;
    private int ticketsAvailable;
    @ManyToOne
    private Category category;
}

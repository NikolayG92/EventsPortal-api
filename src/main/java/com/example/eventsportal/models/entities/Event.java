package com.example.eventsportal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
@Setter
public class Event extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    @Length(min = 15, max = 1000, message = "Description length should be more than 15 and less than 1000 symbols!")
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "tickets_available")
    @Min(value = 0, message = "Tickets cannot be negative!")
    private int ticketsAvailable;

}

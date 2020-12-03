package com.example.eventsportal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category extends BaseEntity{

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "image_url")
    private String imageUrl;
    @OneToMany
    private List<Event> events;
}

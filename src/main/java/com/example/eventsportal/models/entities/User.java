package com.example.eventsportal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity{

    private String username;
    private String email;
    private String password;
    private String imageUrl;
    @OneToMany
    private List<Event> events;
}

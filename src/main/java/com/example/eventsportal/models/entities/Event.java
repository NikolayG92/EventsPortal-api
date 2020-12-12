package com.example.eventsportal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "start_date", nullable = false)
    @FutureOrPresent
    private LocalDate startedDate;
    @Column(name = "tickets_available")
    @Min(value = 0, message = "Tickets cannot be negative!")
    private int ticketsAvailable;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinTable(
            name = "events_users",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<UserEventInfo> users = new HashSet<>();

}

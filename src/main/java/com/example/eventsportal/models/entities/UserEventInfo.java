package com.example.eventsportal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_event_info")
public class UserEventInfo extends BaseEntity{

    @ManyToOne
    private User user;
    @Column(name = "bought_tickets")
    private int boughtTickets;
}

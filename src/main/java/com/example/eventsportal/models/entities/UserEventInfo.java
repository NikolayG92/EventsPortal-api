package com.example.eventsportal.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
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
    @Min(value = 0, message = "Tickets cannot be less than 0!")
    private int boughtTickets;

}

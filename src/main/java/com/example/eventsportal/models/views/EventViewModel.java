package com.example.eventsportal.models.views;

import com.example.eventsportal.models.entities.UserEventInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class EventViewModel {
    
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String startDate;
    private List<UserEventInfo> users;
    private int ticketsAvailable;
}

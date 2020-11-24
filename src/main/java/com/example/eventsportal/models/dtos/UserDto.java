package com.example.eventsportal.models.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String id;
    private String username;
    private String email;
    private String password;
    private String imageUrl;
    private List<EventDto> events;
}

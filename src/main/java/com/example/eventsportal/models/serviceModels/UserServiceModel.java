package com.example.eventsportal.models.serviceModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserServiceModel {

    private String id;
    private String username;
    private String password;
    private String imageUrl;

}

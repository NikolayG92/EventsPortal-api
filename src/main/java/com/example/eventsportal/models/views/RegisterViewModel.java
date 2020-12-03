package com.example.eventsportal.models.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class RegisterViewModel {

    @NotNull
    private String id;
}

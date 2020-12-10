package com.example.eventsportal.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class EventDto {

    private String id;
    @NotNull
    private String name;
    @Length(min = 30)
    private String description;
    @NotNull
    private String imageUrl;
    @NotNull
    @FutureOrPresent
    private String startDate;
    @Min(value = 10)
    private int ticketsAvailable;
    @NotNull
    private CategoryDto category;
}

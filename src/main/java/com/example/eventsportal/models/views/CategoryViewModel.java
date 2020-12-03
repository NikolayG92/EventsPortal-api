package com.example.eventsportal.models.views;

import com.example.eventsportal.models.views.EventViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CategoryViewModel {

    private String id;
    private String name;
    private List<EventViewModel> events;
}

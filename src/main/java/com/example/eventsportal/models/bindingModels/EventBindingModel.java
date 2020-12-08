package com.example.eventsportal.models.bindingModels;

import com.example.eventsportal.models.entities.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class EventBindingModel {

    private String name;
    private String description;
    private int ticketsAvailable;
    private String startDate;
    private String category;
}

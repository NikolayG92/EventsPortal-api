package com.example.eventsportal.models.bindingModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserEditBindingModel {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}

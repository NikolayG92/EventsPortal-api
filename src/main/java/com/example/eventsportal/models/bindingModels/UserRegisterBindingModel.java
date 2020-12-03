package com.example.eventsportal.models.bindingModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class UserRegisterBindingModel {

    @NotNull(message = "Username cannot be empty!")
    @Length(min = 4, message = "Username should be at least 4 characters!")
    private String username;
    @NotNull(message = "Message is required!")
    @Email(message = "Please enter valid email!")
    private String email;
    @NotNull(message = "Password is required!")
    @Length(min = 4, message = "Password length should be at least 4 characters!")
    private String password;
    @NotNull
    private String confirmPassword;
}

package com.example.eventsportal.api;

import com.example.eventsportal.exceptions.UserNotFoundException;
import com.example.eventsportal.models.bindingModels.LoginBindingModel;
import com.example.eventsportal.models.bindingModels.UserEditBindingModel;
import com.example.eventsportal.models.bindingModels.UserRegisterBindingModel;
import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.serviceModels.UserServiceModel;
import com.example.eventsportal.models.views.LoginViewModel;
import com.example.eventsportal.models.views.RegisterViewModel;
import com.example.eventsportal.services.CloudinaryService;
import com.example.eventsportal.services.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {


    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final AuthenticationManager authenticationManager;


    public UserController(UserService userService, ModelMapper modelMapper, CloudinaryService cloudinaryService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.modelMapper = modelMapper;

        this.cloudinaryService = cloudinaryService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<User>> getAllUsers(){

        return ResponseEntity
                .ok()
                .body(this.userService.getAllUsers());
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getCurrentUser(Principal principal){


        User user = this.userService.findUserByUsername(principal.getName());

        return ResponseEntity
                .ok()
                .body(user);
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterViewModel> registerUser(@Valid @RequestBody UserRegisterBindingModel userRegisterBindingModel) {
        RegisterViewModel registerViewModel =
                this.userService.signUpUser(userRegisterBindingModel);

        return ResponseEntity
                .ok()
                .body(registerViewModel);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginViewModel> login(@Valid @RequestBody LoginBindingModel loginBindingModel ) throws UserNotFoundException {
        this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginBindingModel.getUsername(),
                        loginBindingModel.getPassword()));
        return ResponseEntity.ok(
                this.userService.signInUser(loginBindingModel)
        );

    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpSession httpSession){

        httpSession.invalidate();

        return ResponseEntity.ok()
                .build();
    }


    @PostMapping(value = "/edit",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> editUserProfile(@RequestPart("user") @Valid UserEditBindingModel userEditBindingModel,
                                                @RequestPart("file") @Valid MultipartFile file,
                                                Principal principal) throws IOException {

        UserServiceModel userServiceModel =
                this.modelMapper
                .map(this.userService.findUserByUsername(principal.getName()), UserServiceModel.class);

        imgValidate(userServiceModel, file);

        return ResponseEntity
                .ok()
                .body(this.userService.editUser(userEditBindingModel, userServiceModel, principal.getName()));
    }

    private void imgValidate(UserServiceModel userServiceModel, MultipartFile file) throws IOException {
        if (file != null) {
            userServiceModel.setImageUrl(cloudinaryService.uploadImg(file));
        } else {
            userServiceModel.setImageUrl("/img/no-image-available.jpg");
        }
    }


}

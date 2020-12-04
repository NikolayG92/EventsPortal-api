package com.example.eventsportal.api;

import com.example.eventsportal.models.bindingModels.LoginBindingModel;
import com.example.eventsportal.models.bindingModels.UserRegisterBindingModel;
import com.example.eventsportal.models.dtos.UserDto;
import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.models.views.LoginViewModel;
import com.example.eventsportal.models.views.RegisterViewModel;
import com.example.eventsportal.services.UserService;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Resource
    private AuthenticationManager authManager;

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<User>> getAllUsers(){

        return ResponseEntity
                .ok()
                .body(this.userService.getAllUsers());
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getCurrentUser(Principal principal,
                                               HttpSession httpSession){


        User user = this.userService.findUserByUsername(principal.getName());

        return ResponseEntity
                .ok()
                .body(user);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterViewModel> registerUser(@Valid @RequestBody UserRegisterBindingModel userRegisterBindingModel) {
        RegisterViewModel created = this.userService.signUpUser(userRegisterBindingModel);

        return ResponseEntity
                .ok()
                .body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginViewModel> login(@Valid @RequestBody LoginBindingModel loginBindingModel ) {
        LoginViewModel created = this.userService.signInUser(loginBindingModel);

        return ResponseEntity
                .ok()
                .body(created);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpSession httpSession){

        httpSession.invalidate();

        return ResponseEntity.ok()
                .build();
    }


    @PostMapping("/edit")
    public ResponseEntity<User> editUserProfile(@Valid @RequestBody UserDto userDto) {

        return ResponseEntity
                .ok()
                .body(this.userService.editUser(userDto));
    }



}

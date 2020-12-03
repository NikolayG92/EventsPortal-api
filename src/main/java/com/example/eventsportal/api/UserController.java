package com.example.eventsportal.api;

import com.example.eventsportal.models.bindingModels.LoginBindingModel;
import com.example.eventsportal.models.bindingModels.UserRegisterBindingModel;
import com.example.eventsportal.models.dtos.UserDto;
import com.example.eventsportal.models.entities.User;
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

    @GetMapping("/")
    public ResponseEntity<UserDto> findUserById(@RequestParam("id") String id){

        UserDto user = this.userService.findUserById(id);

        if(user != null) {
            return ResponseEntity
                    .ok()
                    .body(user);
        }else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterViewModel> registerUser(@Valid @RequestBody UserRegisterBindingModel userRegisterBindingModel) {
        RegisterViewModel created = this.userService.register(userRegisterBindingModel);

        return ResponseEntity
                .ok()
                .body(created);
    }
    @PostMapping(produces = "application/json")
    @RequestMapping({ "/login" })
    public ResponseEntity<User> login(@Valid @RequestBody LoginBindingModel loginBindingModel, final HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(loginBindingModel.getUsername(), loginBindingModel.getPassword());
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);


        User user = this.userService.findUserByUsername(loginBindingModel.getUsername());
        if(bCryptPasswordEncoder.matches(loginBindingModel.getPassword(), user.getPassword())){
            user.setPassword(loginBindingModel.getPassword());
        }

        return ResponseEntity
                .ok(user);
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logoutUser(HttpSession httpSession){

        httpSession.invalidate();

        return ResponseEntity.ok()
                .body(true);
    }
}

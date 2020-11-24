package com.example.eventsportal.api;

import com.example.eventsportal.models.dtos.UserDto;
import com.example.eventsportal.models.entities.User;
import com.example.eventsportal.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
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
}

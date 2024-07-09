package com.example.demo2;


import com.example.demo2.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public Optional<UserDto> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto){
        return userService.saveUser(userDto);


    }
    @PutMapping("/{id}")
    public UserDto updateUser(@RequestBody UserDto userAtt, @PathVariable UUID id){
        return userService.updateUser(id,userAtt);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
    }

}

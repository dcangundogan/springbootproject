package com.example.demo2;


import com.example.demo2.auth.RolesRepository;
import com.example.demo2.auth.Roles;
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
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository roleRepository;


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
    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
    }
    @PostMapping("/role")
    public Roles createRole(@RequestBody Roles role){
        return userService.saveRole(role);
    }

    @PutMapping("/{id}/role/{rolename}")
    public void addRoleToUser(@PathVariable UUID id, @PathVariable String rolename){
        userService.addRoleToUser(id, rolename);
    }



}

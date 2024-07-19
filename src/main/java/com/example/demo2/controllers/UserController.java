package com.example.demo2.controllers;


import com.example.demo2.entitites.User;
import com.example.demo2.repostories.UserRepository;
import com.example.demo2.services.UserService;
import com.example.demo2.auth.RolesRepository;
import com.example.demo2.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.awt.print.Pageable;
import java.util.*;

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

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<Map<String, Object>> getUsersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest paging = PageRequest.of(page, size);
        Page<User> pageUsers = userRepository.findAll(paging);
        List<User> users = pageUsers.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("total", pageUsers.getTotalElements());
        response.put("currentPage", pageUsers.getNumber());
        response.put("totalPages", pageUsers.getTotalPages());

        System.out.println("Fetched Users: " + users);
        System.out.println("Total Users: " + pageUsers.getTotalElements());

        return ResponseEntity.ok(response);
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

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping("/hepsi")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }






}

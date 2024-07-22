package com.example.demo2.controllers;


import com.example.demo2.entitites.User;
import com.example.demo2.repostories.UserRepository;
import com.example.demo2.services.UserService;
import com.example.demo2.auth.RolesRepository;
import com.example.demo2.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


import java.util.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository roleRepository;
    //@Query(value = "SELECT * FROM tbl_usr",nativeQuery = true)


    @GetMapping("/{id}")
    public Optional<UserDto> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/all")
    public Page<User> getUsersWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "identity_number", required = false) String identity_number,
            @RequestParam(value = "birth_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date birth_date,
            @RequestParam(value = "salary", required = false) Float salary) {
        logger.info("Filtering users with: name={}, surname={}, email={}, identity_number={}, birth_date={}, salary={}",
                name, surname, email, identity_number, birth_date, salary);

        Pageable pageable = PageRequest.of(page, size);
        return userService.findAllUsersWithFilters(pageable, name, surname, email, identity_number, birth_date, salary);
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

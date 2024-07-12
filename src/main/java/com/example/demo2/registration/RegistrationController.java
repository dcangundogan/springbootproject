package com.example.demo2.registration;

import com.example.demo2.entitites.User;
import com.example.demo2.repostories.UserRepository;
import com.example.demo2.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registerRequest) {
        // Create new user
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        // set other fields as needed

        userRepository.save(user);

        // Generate JWT token
        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}

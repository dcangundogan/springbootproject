package com.example.demo2.configs;


import com.example.demo2.dto.LoginUserDto;
import com.example.demo2.dto.RegisterUserDto;
import com.example.demo2.entitites.User;
import com.example.demo2.repostories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
    public User signup(RegisterUserDto input) {
        User user = new User()
                .setName(input.getFullName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);





    }
}

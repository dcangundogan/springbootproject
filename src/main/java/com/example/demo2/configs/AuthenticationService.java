package com.example.demo2.configs;


import com.example.demo2.auth.Roles;
import com.example.demo2.dto.LoginUserDto;
import com.example.demo2.dto.RegisterUserDto;
import com.example.demo2.entitites.User;
import com.example.demo2.repostories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


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
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + input.getEmail()));
    }
    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setName(input.getName());
        user.setSurname(input.getSurname());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setEmail(input.getEmail());
        user.setIdentity_number(input.getIdentityNumber());
        user.setBirth_date(input.getBirthDate());
        user.setSalary(input.getSalary());
        Roles role = new Roles();
        role.setRolename("ROLE_admiun");
        user.setRoles(Collections.singleton(role));


        // Save the user to the repository
        return userRepository.save(user);





    }

}

package com.example.demo2.impl;

import com.example.demo2.auth.Roles;
import com.example.demo2.auth.RolesRepository;
import com.example.demo2.dto.UserDto;
import com.example.demo2.entitites.User;
import com.example.demo2.repostories.UserRepository;
import com.example.demo2.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserDetails {
    private UserRepository userRepository;
    private RolesRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RolesRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void saveNewUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName() + " " + userDto.getSurname());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Roles role = roleRepository.findByRolename("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles((Set<Roles>) Arrays.asList(role));
        userRepository.save(user);

    }



    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setName(str[0]);
        userDto.setSurname(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Roles checkRoleExist(){
        Roles role = new Roles();
        role.setRolename("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}

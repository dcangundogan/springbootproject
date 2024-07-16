package com.example.demo2.services;
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.*;

import com.example.demo2.auth.Roles;
import com.example.demo2.auth.RolesRepository;
import com.example.demo2.repostories.UserRepository;
import com.example.demo2.dto.UserDto;
import com.example.demo2.entitites.User;
import com.example.demo2.logic.UserLogic;
import com.example.demo2.mapper.UserMapper;
import com.example.demo2.token.ConfirmationToken;
import com.example.demo2.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserService {




    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLogic userLogic;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolesRepository rolesRepository;

    @PreAuthorize("hasAuthority('CREATE_USER')")
    public UserDto saveUser(UserDto userDto){
        if (userLogic.validateUser(userDto)) {
            if(!userLogic.validateUserID(userDto)){
                throw  new IllegalArgumentException("User id must be 11 characters!");
            }
            if(!userLogic.validateUserSalary(userDto)){
                throw  new IllegalArgumentException("User salary must be bigger than or equal 0!!!");
            }
            else {
            User user = userMapper.toEntity(userDto);
            user = userRepository.save(user);
            return userMapper.toDTO(user);}
        } else {
            throw new IllegalArgumentException("User data is not mean to be possible!!!");
        }
    }
    @PreAuthorize("hasAuthority('READ_USER')")
    public Optional<UserDto> getUserById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());

    }
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public void deleteUser(UUID id){
         userRepository.deleteById(id);
    }
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public UserDto updateUser(UUID id,UserDto useratt) {
        if (userLogic.validateUser(useratt) ) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id " + id));
            if (!userLogic.validateUserID(useratt)){
                throw new IllegalArgumentException("User id must be 11 digit!");
            }
            if (!userLogic.validateUserSalary(useratt)){
                throw new IllegalArgumentException("User salary must be bigger than 0 !");
            }
            else{
            user.setName(useratt.getName());
            user.setSurname(useratt.getSurname());
            user.setIdentity_number(useratt.getIdentity_number());
            user.setBirth_date(useratt.getBirth_date());
            user.setSalary(useratt.getSalary());
            user.setRoles(user.getRoles());

            return userMapper.toDTO(user);}

        }
        else {
            throw new IllegalArgumentException("User is cannot be updated");
        }



    }


    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        //loading by user id is working right now

        final Optional<User> optionalUser = userRepository.findById(UUID.fromString(id));

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else {
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", id));
        }
    }
    public void signUpUser(User user){

        final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        final User createdUser=userRepository.save(user);
        final ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);


    }

    public void saveasUser(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName()+" " +userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Roles role = rolesRepository.findByRolename("ROLE_ADMIN");
        if(role==null){
            role= checkRoleExist();
        }
        user.setRoles((Set<Roles>) Arrays.asList(role));
        userRepository.save(user);

    }
//    public User findUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//
//    public List<UserDto> findAllUsers() {
//        List<User> users = userRepository.();
//        return users.stream()
//                .map((user) -> mapToUserDto(user))
//                .collect(Collectors.toList());
//    }

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
        return rolesRepository.save(role);
    }



}


package com.example.demo2.services;

import com.example.demo2.repostories.UserRepository;
import com.example.demo2.dto.UserDto;
import com.example.demo2.entitites.User;
import com.example.demo2.logic.UserLogic;
import com.example.demo2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {




    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLogic userLogic;

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


}


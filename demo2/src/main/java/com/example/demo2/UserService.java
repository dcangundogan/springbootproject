package com.example.demo2;

import com.example.demo2.dto.UserDto;
import com.example.demo2.logic.UserLogic;
import com.example.demo2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {




    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLogic userLogic;


    public UserDto saveUser(UserDto userDto){
        if (userLogic.validateUser(userDto)) {
            User user = userMapper.toEntity(userDto);
            user = userRepository.save(user);
            return userMapper.toDTO(user);
        } else {
            throw new IllegalArgumentException("User data is not mean to be possible!!!");
        }
    }
    public Optional<UserDto> getUserById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());

    }
    public void deleteUser(UUID id){
         userRepository.deleteById(id);
    }
    public UserDto updateUser(UUID id,UserDto useratt) {
        if (userLogic.validateUser(useratt)) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id " + id));
            user.setName(useratt.getName());
            user.setSurname(useratt.getSurname());
            user.setIdentity_number(useratt.getIdentity_number());
            user.setBirth_date(useratt.getBirth_date());
            user.setSalary(useratt.getSalary());
            return userMapper.toDTO(user);

        }
        else {
            throw new IllegalArgumentException("User is cannot be updated");
        }


    }
}

package com.example.demo2.mapper;

import com.example.demo2.entitites.User;
import com.example.demo2.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto toDTO(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User toEntity(UserDto userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}

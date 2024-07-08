package com.example.demo2.logic;

import com.example.demo2.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserLogic {

    public boolean validateUser(UserDto userDTO) {

        return userDTO.getBirth_date().before(new Date());
    }


}

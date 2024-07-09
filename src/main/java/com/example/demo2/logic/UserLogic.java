package com.example.demo2.logic;

import com.example.demo2.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserLogic {

    public boolean validateUser(UserDto userDTO) {

        return userDTO.getBirth_date().before(new Date());
    }

    public boolean validateUserID(UserDto userDto){

        return  userDto.getIdentity_number().length() == 11;
        /* In Turkey id numbers must be 11 digits this is why I added to the code to check
        if the id number is 11 digit or not.*/

    }
    public boolean validateUserSalary(UserDto userDto){
        return userDto.getSalary() >0 ;
    }


}

package com.example.demo2.controllers;

import com.example.demo2.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthController {
    @GetMapping("/index")

    public String home(){
        return "index";
    }

    @GetMapping("/register")
    public String showRegistraitonForm(Model model){
        //create model object to form data
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "register";
    }
}

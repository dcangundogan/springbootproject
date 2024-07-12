package com.example.demo2.controllers;

import com.example.demo2.dto.UserDto;
import com.example.demo2.entitites.User;
import com.example.demo2.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {
    UserService userService;
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
    @PostMapping("/register/save")
    public String registration(@Validated @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/login")
    public String login(){
        return "login";

    }
}

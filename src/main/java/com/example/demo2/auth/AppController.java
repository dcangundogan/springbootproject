package com.example.demo2.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @RequestMapping("/admin")
    public String loginAdmin(){
        return "Admin has succesfully entered the system";
    }

}

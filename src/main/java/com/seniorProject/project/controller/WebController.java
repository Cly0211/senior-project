package com.seniorProject.project.controller;

import com.seniorProject.project.model.User;
import com.seniorProject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {
    @Autowired
    UserService userService;

    /**
     * verify email and password to login
     * http method: post
     * http://localhost:8080/login
     */
    @PostMapping("/login")
    public User login(@RequestBody User user){
        if (user.getEmail() == null || user.getEmail().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty())
            throw new RuntimeException("invalid email or username");
        user = userService.login(user);
        return user;
    }

    /**
     * verify email and register
     * http method: post
     * http://localhost:8080/login
     */
    @PostMapping("/register")
    public User register(@RequestBody User user){
        if (user.getEmail() == null || user.getEmail().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty())
            throw new RuntimeException("invalid email or username");
        user = userService.register(user);
        return user;
    }
}

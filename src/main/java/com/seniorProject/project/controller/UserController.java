package com.seniorProject.project.controller;

import com.seniorProject.project.model.User;
import com.seniorProject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * add a new user to database
     * http method: post
     * http://localhost:8080/user/add
     */
    @PostMapping("/add")
    public void addUser(@RequestBody User user){
        try{
            userService.insertUser(user);
        }catch(Exception e){
            throw new RuntimeException("error when inserting user: "+user);
        }
    }

    /**
     * update user password in database
     * http method: put
     * http://localhost:8080/user/update
     */
    @PutMapping("/update")
    public void updateUser(@RequestBody User user){
        try{
            userService.updateUser(user);
        }catch(Exception e){
            throw new RuntimeException("error when updating user: "+user);
        }
    }

    /**
     * delete a user in database
     * http method: delete
     * http://localhost:8080/user/delete/{email}
     */
    @DeleteMapping("/delete/{email}")
    public void singleDelete(@PathVariable String email){
        try{
            userService.deleteUser(email);
        }catch(Exception e){
            throw new RuntimeException("error when deleting user with email: "+email);
        }
    }

    /**
     * get user password in database
     * http method: get
     * http://localhost:8080/user/getPassword/{email}
     */
    @GetMapping("/getPassword/{email}")
    public String getPassword(@PathVariable String email){
        try{
            return userService.selectPassword(email);
        }catch(Exception e){
            throw new RuntimeException("error when searching password with email: "+email);
        }
    }

    /**
     * check if the email exists in database
     */
    @GetMapping("/checkEmail/{email}")
    public Boolean checkEmail(@PathVariable String email){
        try{
            return userService.checkEmail(email);
        }catch(Exception e){
            throw new RuntimeException("error when searching email: "+email);
        }
    }
}

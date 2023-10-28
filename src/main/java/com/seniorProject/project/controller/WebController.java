package com.seniorProject.project.controller;

import com.seniorProject.project.model.User;
import com.seniorProject.project.model.Verification;
import com.seniorProject.project.service.UserService;
import com.seniorProject.project.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
public class WebController {
    @Autowired
    UserService userService;
    @Autowired
    VerificationService verificationService;

    /**
     * sample code for front end
     * import axios from 'axios'
     * <templete>
     *     <input id="email" v-model="user.email">
     *     <input id="password" v-model="user.password">
     *     <button @click="login">Login</button>
     * </templete>
     * <script>
     *     export default{
     *         data(){
     *             return{
     *                 user:{
     *                     email : '',
     *                     password : ''
     *                 }
     *             }
     *         },
     *         methods:{
     *             login(){
     *                 axios.post("http://localhost:8080/login", this.user).then(rest => {
     *                     // do something
     *                 })
     *             }
     *         }
     *     }
     * </script>
     */

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

    /**
     * send email with verification code
     * return 1 if the email was sent successfully
     * return -1 if the email is not registered
     * http method: get
     * http://localhost:8080/sendEmail/{id}
     */
    @GetMapping("/sendEmail/{id}")
    public Integer sendEmail(@PathVariable String id){
        if (id == null || id.isEmpty())
            throw new RuntimeException("email can't be empty");
        return userService.sendEmail(id);
    }

    /**
     * verify the verification code
     * return 1 if correct
     * return -1 if incorrect
     * return -2 if expired
     * http method: post
     * http://localhost:8080/verify
     */
    @PostMapping("/verify")
    public Integer verify(@RequestBody Verification verification){
        String id = verification.getId();
        String verCode = verification.getVerCode();
        if (id == null || id.isEmpty() || verCode == null || verCode.isEmpty())
            throw new RuntimeException("id or verification code can't be empty");
        return verificationService.verify(id,verCode,verification.getExpiredDate());
    }

    /**
     * reset password
     * return 1 if the password was reset successfully
     * return -1 if the email is not registered
     * return -2 if the new password is same as the old one
     * http method: post
     * http://localhost:8080/reset
     */
    @PostMapping("/reset")
    public Integer reset(@RequestBody User user){
        if (user.getEmail() == null || user.getEmail().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty())
            throw new RuntimeException("id or new password code can't be empty");
        return userService.reset(user.getEmail(), user.getPassword());
    }
}

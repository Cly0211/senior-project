package com.seniorProject.project.controller;

import com.seniorProject.project.model.User;
import com.seniorProject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class WebController {
    @Autowired
    UserService userService;

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
}

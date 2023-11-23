package com.seniorProject.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seniorProject.project.model.Encryption;
import com.seniorProject.project.model.User;
import com.seniorProject.project.model.Verification;
import com.seniorProject.project.service.UserService;
import com.seniorProject.project.service.VerificationService;
import com.seniorProject.project.util.CipherUtil;
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
     * return -1 is the email is not registered
     * return -2 if wrong password
     * return 1 if email and password match
     * http method: post
     * http://localhost:8080/login
     */
    @PostMapping("/login")
    public Integer login(@RequestBody Encryption encryption){
        try{
            String aesKey = CipherUtil.rsaDecrypt(encryption.getEncryptedAESkey());
            String plainText = CipherUtil.aesDecrypt(encryption.getCipherText(),aesKey);
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(plainText, User.class);
            if (user.getEmail() == null || user.getEmail().isEmpty()
                    || user.getPassword() == null || user.getPassword().isEmpty())
                throw new RuntimeException("invalid email or password");
            return userService.login(user);
        }catch (Exception e){
            throw new RuntimeException("error when converting json to class");
        }
    }

    /**
     * verify email and register
     * return -1 if the email has already registered
     * return 1 if successfully registered
     * http method: post
     * http://localhost:8080/register
     */
    @PostMapping("/register")
    public Integer register(@RequestBody Encryption encryption){
        try{
            String aesKey = CipherUtil.rsaDecrypt(encryption.getEncryptedAESkey());
            String plainText = CipherUtil.aesDecrypt(encryption.getCipherText(),aesKey);
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(plainText, User.class);
            if (user.getEmail() == null || user.getEmail().isEmpty()
                    || user.getPassword() == null || user.getPassword().isEmpty())
                throw new RuntimeException("invalid email or password");
            return userService.register(user);
        }catch (Exception e){
            throw new RuntimeException("error when converting json to class");
        }
    }

    /**
     * send email with verification code for resetting password
     * return 1 if the email was sent successfully
     * return -1 if the email is not registered
     * http method: get
     * http://localhost:8080/sendResetEmail/{id}
     */
    @GetMapping("/sendResetEmail/{id}")
    public Integer sendResetEmail(@PathVariable String id){
        if (id == null || id.isEmpty())
            throw new RuntimeException("email can't be empty");
        if (!userService.checkEmail(id))
            return -1;
        return userService.sendEmail(id);
    }

    /**
     * send email with verification code for registration
     * return 1 if the email was sent successfully
     * return -1 if the email has been registered
     * http method: get
     * http://localhost:8080/sendRegEmail/{id}
     */
    @GetMapping("/sendRegEmail/{id}")
    public Integer sendRegEmail(@PathVariable String id){
        if (id == null || id.isEmpty())
            throw new RuntimeException("email can't be empty");
        if (userService.checkEmail(id))
            return -1;
        return userService.sendEmail(id);
    }

    /**
     * verify the verification code
     * return 1 if correct
     * return -1 if incorrect
     * return -2 if expired
     * return -3 if the user didn't send verification code
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
    public Integer reset(@RequestBody Encryption encryption){
        try{
            String aesKey = CipherUtil.rsaDecrypt(encryption.getEncryptedAESkey());
            String plainText = CipherUtil.aesDecrypt(encryption.getCipherText(),aesKey);
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(plainText, User.class);
            if (user.getEmail() == null || user.getEmail().isEmpty()
                    || user.getPassword() == null || user.getPassword().isEmpty())
                throw new RuntimeException("invalid email or password");
            return userService.reset(user.getEmail(), user.getPassword());
        }catch (Exception e){
            throw new RuntimeException("error when converting json to class");
        }
    }
}

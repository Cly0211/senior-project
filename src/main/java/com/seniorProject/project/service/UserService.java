package com.seniorProject.project.service;


import cn.hutool.crypto.digest.BCrypt;
import com.seniorProject.project.mapper.UserMapper;
import com.seniorProject.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Component
public class UserService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    UserMapper userMapper;
    @Autowired
    VerificationService verificationService;

    private String from = "seniorprojectgroup13@gmail.com";
    private String subject = "[Mindful Journey] Forget Password?";
    private String text1 = "Dear user,\n\nYour verification code is ";
    private String text2 = ". The code will expire in five minutes.\n\nBest,\nGroup 13";

    /**
     * add a new user to database
     */
    public void insertUser(User user){
        String encrypted = BCrypt.hashpw(user.getPassword());
        user.setPassword(encrypted);
        userMapper.insert(user);
    }

    /**
     * update user password in database
     */
    public void updateUser(User user) {
        String encrypted = BCrypt.hashpw(user.getPassword());
        user.setPassword(encrypted);
        userMapper.updatePassword(user);
    }

    /**
     * delete a user according to email
     */
    public void deleteUser(String email) {
        userMapper.delete(email);
    }

    /**
     * get user password in database
     */
    public String selectPassword(String email) {
        return userMapper.selectPassword(email);
    }

    /**
     * check if the email exists in database
     */
    public Boolean checkEmail(String email){
        User dbUser = userMapper.selectUser(email);
        return dbUser != null;
    }

    /**
     * verify email and password to login
     */
    public Integer login(User user) {
        User dbUser = userMapper.selectUser(user.getEmail());
        if (dbUser == null){
            return -1;
        }
        if (!BCrypt.checkpw(user.getPassword(),dbUser.getPassword())){
            return -2;
        }
        return 1;
    }

    /**
     * verify email and add new user to database
     */
    public Integer register(User user) {
        User dbUser = userMapper.selectUser(user.getEmail());
        if (dbUser != null)
            return -1;
        insertUser(user);
        return 1;
    }

    /**
     * send email with verification code
     */
    public Integer sendEmail(String email) {
        String verCode = String.valueOf(new Random().nextInt(9000) + 1000);
        Date date = new Date();
        // send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email+"@case.edu");
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text1+verCode+text2);
        message.setSentDate(date);
        javaMailSender.send(message);

        //add verification code to database
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 5);
        verificationService.addCode(email, verCode, calendar.getTime());
        return 1;
    }

    public Integer reset(String id, String newPassword) {
        User dbUser = userMapper.selectUser(id);
        if (dbUser == null)
            return -1;
        if (BCrypt.checkpw(newPassword,dbUser.getPassword()))
            return -2;
        dbUser.setPassword(newPassword);
        updateUser(dbUser);
        return 1;
    }
}

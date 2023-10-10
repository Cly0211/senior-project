package com.seniorProject.project.service;

import com.seniorProject.project.mapper.UserMapper;
import com.seniorProject.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    User user;
    @Autowired
    UserMapper userMapper;

    /**
     * add a new user to database
     */
    public void insertUser(User user){
        userMapper.insert(user);
    }

    /**
     * update user password in database
     */
    public void updateUser(User user) {
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
    public User login(User user) {
        User dbUser = userMapper.selectUser(user.getEmail());
        if (dbUser == null){
            throw new RuntimeException("email is not registered: "+user.getEmail());
        }
        if (!user.getPassword().equals(dbUser.getPassword())){
            throw new RuntimeException("incorrect password ("+user.getPassword()+") for email "+user.getEmail());
        }
        return dbUser;
    }

    /**
     * verify email and add new user to database
     */
    public User register(User user) {
        User dbUser = userMapper.selectUser(user.getEmail());
        if (dbUser != null)
            throw new RuntimeException("the email has been registered");
        userMapper.insert(user);
        return user;
    }
}

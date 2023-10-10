package com.seniorProject.project.controller;

import com.seniorProject.project.model.ChatMessage;
import com.seniorProject.project.model.OnlineUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
public class ChatController {
    @Autowired
    private OnlineUsers onlineUsers;
    @MessageMapping("/chatroom.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage message){
        return message;
    }

    @MessageMapping("/chatroom.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor){
        onlineUsers.addUsername(message.getSender(), true);
        //add username in websocket session
        headerAccessor.getSessionAttributes().put("username",message.getSender());
        return message;
    }

    @GetMapping("/online-users")
    @ResponseBody
    public List<String> getOnlineUsers() {
        List<String> onlineUsernames = new ArrayList<>(onlineUsers.getOnlineUsernames());
        return onlineUsernames;
    }
}

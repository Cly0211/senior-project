package com.seniorProject.project.model;

import com.seniorProject.project.enums.MessageType;
import lombok.Data;

@Data
public class ChatMessage {
    private String messageBody;
    private String sender;
    private MessageType type;

    public ChatMessage (String messageBody, String sender, MessageType type){
        this.messageBody = messageBody;
        this.sender = sender;
        this.type = type;
    }
}

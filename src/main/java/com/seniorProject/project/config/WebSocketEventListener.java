package com.seniorProject.project.config;

import com.seniorProject.project.enums.MessageType;
import com.seniorProject.project.model.OnlineUsers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import com.seniorProject.project.model.ChatMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemplete;
    @Autowired
    private OnlineUsers onlineUsers;

    @EventListener
    public  void handelWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String)headerAccessor.getSessionAttributes().get("username");
        if (username != null){
            log.info("user disconnected: {}",username);
            ChatMessage message = new ChatMessage(null,username, MessageType.LEAVE);
            messageTemplete.convertAndSend("/topic/public",message);
            onlineUsers.removeUsername(username);
        }
    }
}

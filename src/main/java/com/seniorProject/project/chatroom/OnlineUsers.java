package com.seniorProject.project.chatroom;

import com.seniorProject.project.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OnlineUsers {
    private Map<String, Boolean> onlineUserList = new ConcurrentHashMap<String, Boolean>();

    public Set<String> getOnlineUsernames() {
        return onlineUserList.keySet();
    }
    public void addUsername(String username, Boolean b){
        onlineUserList.put(username, b);
    }

    public boolean removeUsername(String username){
        return onlineUserList.remove(username);
    }

    public boolean containsUserName(String username){
        return onlineUserList.containsKey(username);
    }
}

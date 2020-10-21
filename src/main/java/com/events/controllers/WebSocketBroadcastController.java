package com.events.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.events.models.ChatMessage;
 
@Controller
public class WebSocketBroadcastController {
 
    @GetMapping("/stomp-broadcast")
    public String getWebSocketBroadcast() {
        return "stomp-broadcast.html";
    }
    
    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    public ChatMessage send(ChatMessage chatMessage) throws Exception {
        return new ChatMessage(chatMessage.getFrom(), chatMessage.getText(), "ALL");
    }
}
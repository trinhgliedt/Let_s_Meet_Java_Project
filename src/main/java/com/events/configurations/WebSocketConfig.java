package com.events.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.events.handler.MyTextWebSocketHandler;

//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//  @Override
//  public void configureMessageBroker(MessageBrokerRegistry config) {
//    config.enableSimpleBroker("/topic");
//    config.setApplicationDestinationPrefixes("/app");
//  }
//
//  @Override
//  public void registerStompEndpoints(StompEndpointRegistry registry) {
//    registry.addEndpoint("/gs-guide-websocket").withSockJS();
//  }
//
//}

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
 
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new MyTextWebSocketHandler(), "/web-socket");
    }
}
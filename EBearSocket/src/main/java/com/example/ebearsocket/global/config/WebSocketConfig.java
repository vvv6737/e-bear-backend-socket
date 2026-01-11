package com.example.ebearsocket.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 처음 클라이언트가 웹소켓 연결 시도 주소
        registry.addEndpoint("/ws-stomp") // 연결 주소
                .setAllowedOriginPatterns("*")
                .withSockJS(); // 구형 브라우저 대응
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 사용자가 메세지 보낼 때 (Client -> Server)
        // 클라이언트가 메시지를 보낼 때 사용하는 주소
        registry.setApplicationDestinationPrefixes("/pub");

        // 사용자가 메세지 받을(구독) 때 (Server -> Client)
        // 서버가 클라이언트에게 메시지를 뿌려줄 때 사용하는 주소(브로드캐스팅)
        registry.enableSimpleBroker("/sub", "/queue");

        // 1:1 개인 메시지 보낼 때 사용할 사용자별 접두사
        registry.setUserDestinationPrefix("/user");
    }
}

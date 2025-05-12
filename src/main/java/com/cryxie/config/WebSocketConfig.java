package com.cryxie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    final int PORTA_STOMP = 61613;
    final String NOME_SERVICO_EM_DOCKER_COMPOSE = "rabbitmq";

    // @Override
    // public void configureMessageBroker(MessageBrokerRegistry config) {
    // config.enableSimpleBroker("/topic");
    // config.setApplicationDestinationPrefixes("/app");
    // }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Dispara tudo pro RabbitMQ
        config.enableStompBrokerRelay("/topic")
                .setRelayHost(NOME_SERVICO_EM_DOCKER_COMPOSE)
                .setRelayPort(PORTA_STOMP)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.0/sockjs.min.js");
    }
}
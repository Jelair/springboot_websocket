package com.uduck.springboot_websocket.config;

import com.uduck.springboot_websocket.interceptor.MyChannelInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBySTOMPConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * /portfolio is the HTTP URL for the endpoint to which a WebSocket
         * (or SockJS) client needs to connect for the WebSocket handshake.
         */
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * STOMP messages whose destination header begins with /app
         * are routed to @MessageMapping methods in @Controller classes.
         */
        registry.setApplicationDestinationPrefixes("/apple");
        /**
         * Use the built-in message broker for subscriptions and broadcasting
         * and route messages whose destination header begins with /topic `or
         * `/queue to the broker.
         */
        registry.enableSimpleBroker("/topic","/queue");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(myChannelInterceptor());
    }

    @Bean
    public MyChannelInterceptor myChannelInterceptor(){
        return new MyChannelInterceptor();
    }
}

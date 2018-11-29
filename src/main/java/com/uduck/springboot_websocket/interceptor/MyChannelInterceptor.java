package com.uduck.springboot_websocket.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.security.Principal;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

public class MyChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private CopyOnWriteArraySet<String> online_sessionid = new CopyOnWriteArraySet<>();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        //StompCommand command = accessor.getCommand();
        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        String sessionId = accessor.getSessionId();

        if (StompCommand.SUBSCRIBE.equals(command)){
            online_sessionid.add(sessionId);
            messagingTemplate.convertAndSend("/topic/online_user", String.valueOf(online_sessionid.size()));
        }

        if (StompCommand.DISCONNECT.equals(command)){
            online_sessionid.remove(sessionId);
            messagingTemplate.convertAndSend("/topic/online_user", String.valueOf(online_sessionid.size()));
        }
    }
}

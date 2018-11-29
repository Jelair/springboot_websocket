package com.uduck.springboot_websocket.config;

import com.uduck.springboot_websocket.handler.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

//@Configuration
//@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(myHandler(),"/myhandler")
                /**
                 * 定制初始HTTP WebSocket握手请求最简单的方法是通过HandshakeInterceptor，
                 * 它为握手“之前”和“之后”公开方法。您可以使用这样的拦截器来阻止握手，
                 * 或者为WebSocketSession提供任何属性。
                 * 下面的例子使用一个内置的拦截器将HTTP会话属性传递给WebSocket会话:
                 */
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                /**
                 * Allow only same-origin requests (default):
                 * In this mode, when SockJS is enabled,
                 * the Iframe HTTP response header X-Frame-Options is set to SAMEORIGIN,
                 * and JSONP transport is disabled, since it does not allow checking the
                 * origin of a request. As a consequence, IE6 and IE7 are not supported
                 * when this mode is enabled.
                 *
                 * Allow a specified list of origins:
                 * Each allowed origin must start with http:// or https://.
                 * In this mode, when SockJS is enabled, IFrame transport is
                 * disabled. As a consequence, IE6 through IE9 are not supported
                 * when this mode is enabled.
                 *
                 * Allow all origins:
                 * To enable this mode, you should provide * as the allowed origin value.
                 * In this mode, all transports are available.
                 */
                .setAllowedOrigins("http://mydomain.com");
    }

    @Bean
    public WebSocketHandler myHandler(){
        return new MyHandler();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer(){
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxBinaryMessageBufferSize(8192);
        container.setMaxTextMessageBufferSize(8192);
        return container;
    }
}

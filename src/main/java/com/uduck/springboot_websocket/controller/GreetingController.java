package com.uduck.springboot_websocket.controller;

import com.uduck.springboot_websocket.hello.Greeting;
import com.uduck.springboot_websocket.hello.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws InterruptedException {
        System.out.println("Controller ========> Receive a message.");
        Thread.sleep(1000);
        //return new Greeting("Hello,"+ message.getName());
        return new Greeting("Hello,"+ HtmlUtils.htmlEscape(message.getName()));
    }
}

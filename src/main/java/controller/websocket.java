package controller;

import mappingObj.forWebsocket.message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class websocket {

    @MessageMapping("/message")
    @SendTo("/topic/test")
    public message test(message ms) throws InterruptedException {
        System.out.println("something touch me");
        Thread.sleep(1000);
        return new message("i throw back "+ ms.getContent());
    }
}

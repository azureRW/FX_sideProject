package controller;

import mappingObj.forWebsocket.message;
import model.userBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


import java.security.Principal;


@Controller
public class websocket {
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    userBehavior behavior;
    @MessageMapping("message")
    public String test(message ms,Principal principal) throws InterruptedException {
        System.out.println("test is run");
//        return new message("i throw back "+ ms.getContent());
        return "tesr";
    }
    @MessageMapping("trade/buy")
    public void buy(message ms,Principal principal){
         template.convertAndSend("/topic/"+principal.getName().trim()
                ,new message(behavior.buy(1,ms.getContent())));
    }
    @MessageMapping("trade/sell")
    public void sell(message ms,Principal principal){
        template.convertAndSend("/topic/"+principal.getName().trim()
                ,new message(behavior.sell(1,ms.getContent())));
    }
    @MessageMapping("trade/offset")
    public void offset(message ms,Principal principal){
         template.convertAndSend("/topic/"+principal.getName().trim()
                ,new message(behavior.offset(ms.getContent())));
    }

//    public void broadcastPrice(Float ask,Float bid){
//        Map<String,String> map = new HashMap<>();
//        map.put("ask",ask.toString());
//        map.put("bid",bid.toString());
//        map.put("time",new Date().toString());
//        template.convertAndSend("/topic/price",map);
//    }
//i am moved to aop package

    @MessageMapping("/login")
    public void login(@Header("simpSessionId") String sessionId) {
    System.out.println(sessionId);
}
}

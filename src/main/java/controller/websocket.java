package controller;

import mappingObj.forWebsocket.message;
import model.userBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


import java.security.Principal;
import java.util.HashMap;


@Controller
public class websocket {
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    userBehavior behavior;
    @MessageMapping("/message")
    public void test(message ms,Principal principal) throws InterruptedException {
        System.out.println("test is run");
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.DISCONNECT);
        headerAccessor.setSessionId(principal.getName().trim());
        headerAccessor.setLeaveMutable(true);
        template.convertAndSend("/topic/"+principal.getName().trim(),"",headerAccessor.getMessageHeaders());
//        return new message("i throw back "+ ms.getContent());
//        return "tesr";
    }
    @MessageMapping("/trade/buy")
    public void doBuy(message ms,Principal principal){
         template.convertAndSend("/topic/"+principal.getName().trim()
                ,new message(behavior.buy(1,ms.getContent())));
    }
    @MessageMapping("/trade/sell")
    public void doSell(message ms,Principal principal){
        template.convertAndSend("/topic/"+principal.getName().trim()
                ,new message(behavior.sell(1,ms.getContent())));
    }
    @MessageMapping("/trade/offset")
    public void doOffset(message ms,Principal principal){
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

    @MessageMapping("/trade/showHistory")
    public void history(message ms,Principal principal){
        System.out.println("history");
        template.convertAndSend("/topic/"+principal.getName().trim(),
                behavior.history(ms.getContent().trim()));
    }
    @MessageMapping("/login")
    public void login(@Header("simpSessionId") String sessionId) {
    System.out.println(sessionId);
}


}

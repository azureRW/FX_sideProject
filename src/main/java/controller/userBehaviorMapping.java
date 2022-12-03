package controller;

import model.forWebsocket.message;
import model.deep.semiPersistence;
import service.userBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/userBehavior")
@CrossOrigin
public class userBehaviorMapping {
    @Autowired
    userBehavior behavior;
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    semiPersistence semi;

    @PostMapping ("/sell")
    public String sell(@RequestHeader(value = "id") String id,@RequestBody Map<String,String> map){
        System.out.println("sell is called, content= "+map.get("content"));
         template.convertAndSend("/topic/"+id
                ,new message(behavior.userXsell(semi.uuidToAcount(id),1)));
        return "sell success";
    }
   @PostMapping ("/buy")
    public String buy(@RequestHeader(value = "id") String id,@RequestBody Map<String,String> map){
        System.out.println("buy is called, content="+map.get("content"));
         template.convertAndSend("/topic/"+id
                ,new message(behavior.userXbuy(semi.uuidToAcount(id),1)));
        return "buy success";
    }
    @GetMapping("/test")
    public String test(@RequestHeader(value = "id") String id){
        System.out.println("=============");
        System.out.println("do test");
        behavior.userXtest(semi.uuidToAcount(id));
        return "test compelte";
    }
    @GetMapping("/offset")
    public String offset(@RequestHeader(value = "id") String id){
        System.out.println("===============");
        System.out.println("do offset");
        String res=behavior.userXoffset(semi.uuidToAcount(id));
        template.convertAndSend("/topic/"+id,new message(res));
        return "offset";
    }
    @GetMapping("/history")
    public String history(@RequestHeader(value = "id") String id){
        template.convertAndSend("/topic/"+id,
                behavior.history(semi.uuidToAcount(id)));
       return "history";
    }
    @GetMapping("/logout")
    public String logout(@RequestHeader(value = "id")String id){
        behavior.logout(id);
        return "logout";
    }
}

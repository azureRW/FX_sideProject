package controller;

import mappingObj.forWebsocket.message;
import model.deep.semiPersistence;
import model.userBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/userBehavior/userBehavior")
@CrossOrigin
public class userBehaviorMapping {
    @Autowired
    userBehavior behavior;
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    semiPersistence semi;

    @PostMapping ("/sell")
    public String sell(@RequestHeader String header){
         template.convertAndSend("/topic/"+header
                ,new message(behavior.buy(1,semi.uuidToAcount(header))));
        return "sell success";
    }
//    @PostMapping("/buy")
//    public String buy(@RequestBody Map<String,Integer> map){
//        System.out.println("buy!!!!!!!");
//        return behavior.buy(map.get("unit"));
//    }
//    @GetMapping("/test")
//    public String test(){
//        behavior.test();
//        return "test compelte";
//    }
//    @GetMapping("/offset")
//    public String offset(){
//        return behavior.offset();
//    }
}

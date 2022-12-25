package controller;

import model.PO.userRecode;
import model.forWebsocket.message;
import model.deep.semiPersistence;
import model.service.userBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    private Logger log = LoggerFactory.getLogger(userBehaviorMapping.class);

    @PostMapping ("/sell")
    public String sell(@RequestHeader(value = "id") String id,@RequestBody Map<String,String> map){

        log.info("user '{}' do sell trade",id);
        if(behavior.checkUserPropertyFroSell(id)) {
            log.info("trade fail");
            template.convertAndSend("/topic/" + id
                , new message("Insufficient margin"));
            return "Insufficient margin";}
        else{
            log.info("trade success");
            template.convertAndSend("/topic/"+id
                ,new message(behavior.userXsell(semi.uuidToAcount(id),1)));
        return "sell success";}
    }
   @PostMapping ("/buy")

    public String buy(@RequestHeader(value = "id") String id,@RequestBody Map<String,String> map){

       log.info("user '{}' do buy trade",id);
       if(behavior.checkUserPropertyForBuy(id)){
           log.info("trade fail");
            template.convertAndSend("/topic/" + id
                    , new message("Insufficient margin"));
            return "Insufficient margin";}
        else {
            log.info("trade success");
            template.convertAndSend("/topic/" + id
                    , new message(behavior.userXbuy(semi.uuidToAcount(id), 1)));
            return "buy success";
        }
    }
    @GetMapping("/test")
    public String test(@RequestHeader(value = "id") String id){
        log.info("test");
        behavior.userXtest(semi.uuidToAcount(id));
        return "test complete";
    }
    @GetMapping("/offset")
    public String offset(@RequestHeader(value = "id") String id){
        log.info("user '{}' offset",id);
        String res=behavior.userXoffset(semi.uuidToAcount(id));
        template.convertAndSend("/topic/"+id,new message(res));
        return "offset";
    }
    @GetMapping("/history")
    public String history(@RequestHeader(value = "id") String id){
        List<userRecode> list = behavior.history(semi.uuidToAcount(id));
        log.info("user '{}' access trade history",id);
        template.convertAndSend("/topic/history/"+id, list);
       return "history";
    }
    @GetMapping("/logout")
    public String logout(@RequestHeader(value = "id")String id){
        behavior.logout(id);
        log.info(id+" call logout");
        return "logout";
    }
}

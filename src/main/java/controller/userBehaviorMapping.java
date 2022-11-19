package controller;

import model.userBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/userBehavior")
@CrossOrigin
public class userBehaviorMapping {
    @Autowired
    userBehavior behavior;
    @PostMapping ("/sell")
    public String sell(@RequestBody Map<String, Integer> map){
        System.out.println("sell!!!!!!!!");
        return behavior.sell(map.get("unit"));
    }
    @PostMapping("/buy")
    public String buy(@RequestBody Map<String,Integer> map){
        System.out.println("buy!!!!!!!");
        return behavior.buy(map.get("unit"));
    }
    @GetMapping("/test")
    public String test(){
        behavior.test();
        return "test compelte";
    }
}

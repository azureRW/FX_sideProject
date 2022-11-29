package controller;

import lombok.NoArgsConstructor;
import model.userBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import security.newLogin;

import java.util.HashMap;

@RestController
@RequestMapping("/rAndL")
@CrossOrigin
public class registerAndLogin {
    @Autowired
    private userBehavior behavior;
    @Autowired//cuz log. info. will pass a lot of filters ,it has to be managed by framework
    private newLogin newLogin;
@GetMapping("/test")
    public String test(){
    System.out.println("successful");
        return "test successful";
    }
    @PostMapping(path = "register")
    public String register(@RequestBody HashMap<String,String> map){
    return behavior.register(map.get("account"),map.get("password"));
    }
    @PostMapping(path = "login")
    public  String login(@RequestBody HashMap<String,String> map){
//    return behavior.login(map.get("account"), map.get("password"));

        return newLogin.login(map);
    }

}

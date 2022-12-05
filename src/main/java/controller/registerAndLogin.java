package controller;

import model.service.userBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import model.service.newLogin;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@RestController
@RequestMapping("/rAndL")
@CrossOrigin
public class registerAndLogin {
    private Logger log = LoggerFactory.getLogger(registerAndLogin.class);
    @Autowired
    private userBehavior behavior;
    @Autowired//cuz log. info. will pass a lot of filters ,it has to be managed by framework
    private newLogin newLogin;

    @PostMapping(path = "register")
    public String register(@RequestBody HashMap<String,String> map){
        log.info("user '{}' register",map.get("account"));
    return behavior.register(map.get("account"),map.get("password"));
    }
    @PostMapping(path = "login")
    public  String login(@RequestBody HashMap<String,String> map) throws UnsupportedEncodingException {
//    return behavior.login(map.get("account"), map.get("password"));
        log.info("user '{}' login",map.get("account"));
        return newLogin.login(map);
    }

}

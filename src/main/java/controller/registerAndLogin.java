package controller;

import model.DTO.catchUser;
import model.service.userBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import model.service.newLogin;

import javax.validation.Valid;
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
    public String register(@RequestBody @Validated catchUser user){
//        log.info("user '{}' register",map.get("account"));
        log.info("用戶{}進行註冊",user.getAccount());
    return behavior.register(user.getAccount(),user.getPassword());
    }
    @PostMapping(path = "login")
    public  String login(@RequestBody catchUser user) throws UnsupportedEncodingException {
//    return behavior.login(map.get("account"), map.get("password"));
//        log.info("user '{}' login",map.get("account"));
        log.info("用戶{}T嘗試登入",user.getAccount());
        return newLogin.login(user);
    }

}

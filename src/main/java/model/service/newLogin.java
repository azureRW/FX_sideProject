package model.service;

import model.deep.semiPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import security.implUserDetail;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@Service
public class newLogin {

    @Autowired//from AuthenticationManager in security config, we declared it in config then we can access it here
    private AuthenticationManager authenticationManager;
    @Autowired
    semiPersistence semi;
    public String login(Map<String,String> map) throws UnsupportedEncodingException {
        String encodeAccount = new String(Base64.getDecoder().decode(map.get("account")),"utf-8");
        String encodePassword = new String(Base64.getDecoder().decode(map.get("password")),"utf-8");



        //package account and password and then authenticate can use it
        UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(encodeAccount,encodePassword);
        //authenticate access token and enter userDetail to making sure that token content is in database
         Authentication authenticate =authenticationManager.authenticate(token);
         // if authenticate = null, it means token content is not in DB
        if(Objects.isNull(authenticate) ) throw new RuntimeException("wrong");



        semi.userTokerSet(encodeAccount);//set token in map
        return "successes and Token is "+ semi.account2Uuid(encodeAccount);
    }
}

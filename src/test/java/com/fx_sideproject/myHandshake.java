package com.fx_sideproject;

import com.sun.security.auth.UserPrincipal;
import model.deep.semiPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;
//@Controller
public class myHandshake extends DefaultHandshakeHandler {
    private Logger log = LoggerFactory.getLogger(myHandshake.class);
    @Autowired
    private semiPersistence semi;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        System.out.println("=================");
        System.out.println(request.getPrincipal().getName());
        System.out.println("================");
        final String randomID = UUID.randomUUID().toString();
        log.info("user id :'{}' open a session" ,randomID);
        System.out.println("user id "+ randomID + " is connect");
//        semi.setUserToken(randomID);
        return new UserPrincipal(randomID);
    }
}

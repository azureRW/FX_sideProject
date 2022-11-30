package config;

import model.deep.forServerToken;
import model.deep.semiPersistence;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Configuration
@EnableWebSocketMessageBroker
public class websocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private forServerToken semi;
    @Autowired
    private myHandshake hand;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //prefixes for client
        registry.setApplicationDestinationPrefixes("/app");
        //messages from clients will be sent to the sub-protocols of this
        // sub-endpoint address is written in MessageMapping anno.

        //prefixes for server
        registry.enableSimpleBroker("/topic", "/queue", "/user");
        //the one who subscribes this broker can get (broadcast) message
        // its sub-protocol will be written in SendTo anno.
        // something different between applicationDestination
        // is the address written in SendTo anno. have to be
        // a full address for ex: @Sendto("/topic/test")


    }
        @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //register endpoint, people who went to send or catch message must connect this endpoint first
        registry.addEndpoint("/websocket/"+semi.getToken())
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(hand)
                .withSockJS();
    }


}

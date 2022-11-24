package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class websocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        //prefixes for client
        registry.setApplicationDestinationPrefixes("/app");//messages from clients will be sent to the sub-endpoints of this
                                                          // sub-endpoint address is write in MessageMapping anno.
        //prefixes for server
        registry.enableSimpleBroker("/topic");//the one who subscribes this broker can get (broadcast) message
    }                                                         // its sub-endpoints will be written in SendTo anno.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //enrol endpoint, people who went to send or catch message must connect this endpoint first
        registry.addEndpoint("/websocket").setAllowedOriginPatterns("*").withSockJS();
    }
}

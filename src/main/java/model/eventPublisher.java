package model;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class eventPublisher {
    public final ApplicationEventPublisher publisher;
    eventPublisher(ApplicationEventPublisher publisher){
        this.publisher = publisher;
    }
    public void publisherEvent(unitTest2 event){
        publisher.publishEvent(event);
    }
}

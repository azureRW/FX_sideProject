package scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class mySchedule {
    private Logger log = LoggerFactory.getLogger(mySchedule.class);
    @Scheduled(fixedRate = 2500)
    public void askCurrentFxPrice(){

    }
}

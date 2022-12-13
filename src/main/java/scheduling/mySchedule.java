package scheduling;

import model.deep.semiPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class mySchedule {
    private Logger log = LoggerFactory.getLogger(mySchedule.class);
    @Autowired
    private semiPersistence semi;
    @Value(value = "${displayPrice}")
    private Boolean bForDisplay;
    @Scheduled(fixedRate = 2400)
    public void askCurrentFxPrice() throws IOException, InterruptedException {
        semi.newMainService();

        if(bForDisplay) log
                .info(String.valueOf("ask price is : "+semi.getAsk()+"  bid price is : "+semi.getBid()));
    }
}

package model;

import lombok.Data;
import mappingObj.catchJsonFather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashMap;

@Controller
@Data
public class semiPersistence{
    private float bid;
    private float ask;
    private  Boolean b;
    @Autowired
    private catchJsonFather father;
    @Autowired
    private model.metaAPI metaAPI;
    @Autowired
    unitTest unit;
    public void mainService() throws IOException, InterruptedException {

        if (!metaAPI.isdeploy){
            metaAPI.deployAccount();
            Thread.sleep(2500);
            while(!metaAPI.AccountStatusTest()){
                Thread.sleep(1000);
            }
            Thread.sleep(5000);
        }
        while(true) {
            HashMap<String, Float> map = metaAPI.getCurrentPrice();
            System.out.println(map.get("bid") + " " + map.get("ask"));
            this.bid = map.get("bid");
            this.ask = map.get("ask");
            Thread.sleep(3000);
        }
    }
    //use lambda method to implement asking price in multiple threads environment
    public void callMainService(){
        Thread myThread = new Thread(()->{
            try {
                this.mainService();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
       });
        myThread.start();
    }


}

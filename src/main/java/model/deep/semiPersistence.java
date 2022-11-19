package model.deep;

import mappingObj.catchJsonFather;
import model.unitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
public class semiPersistence  {
    private Date date =new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    private String WD = sdf.format(date);
    private float bid;
    private float ask;
    private  Boolean b;
    @Autowired
    private catchJsonFather father;
    @Autowired
    private model.deep.metaAPI metaAPI;
    @Autowired
    unitTest unit;


    public void mainService () throws IOException, InterruptedException {
        if(WD.equals("星期六")||WD.equals("星期日")){
            System.out.println("in weekend, we will use fake data");
            this.bid=1.03221f;
            this.ask=1.03240f;

            while(true){
                this.bid=this.bid+0.0005f;
                this.ask=this.ask+0.0005f;
                System.out.println("bid="+this.bid+" ask="+this.ask);
                Thread.sleep(3000);
            }

        }
        else {
            if (!metaAPI.isdeploy) {
                metaAPI.deployAccount();
                Thread.sleep(2500);
                while (!metaAPI.AccountStatusTest()) {
                    Thread.sleep(1000);
                }
                Thread.sleep(5000);
            }
            while (true) {
                HashMap<String, Float> map = metaAPI.getCurrentPrice();
                System.out.println("bid="+map.get("bid") + " ask=" + map.get("ask"));
                this.bid = map.get("bid");
                this.ask = map.get("ask");
                Thread.sleep(3000);
            }
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

//////////////////Getter&Setter////////
    public float getBid() {
        return bid;
    }

    public void setBid(float bid) {
        this.bid = bid;
    }

    public float getAsk() {
        return ask;
    }

    public void setAsk(float ask) {
        this.ask = ask;
    }

    public Boolean getB() {
        return b;
    }

    public void setB(Boolean b) {
        this.b = b;
    }
}

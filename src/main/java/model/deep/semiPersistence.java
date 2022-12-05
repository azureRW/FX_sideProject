package model.deep;

import model.dao.catchJsonFather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class semiPersistence  {
    private Date date =new Date();
    private Map<String,String> IdAccount = new HashMap<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    private String WD = sdf.format(date);
    private float bid;
    private float ask;
    private  Boolean b;
    @Autowired
    private catchJsonFather father;
    @Autowired
    private model.service.metaAPI metaAPI;
//    @Autowired
//    unitTest unit;
    @Autowired
    subServiceOfSemi sub;








public void userTokerSet(String userAccount){
    this.IdAccount.put(userAccount,UUID.randomUUID().toString());
    this.IdAccount.put(IdAccount.get(userAccount),userAccount);
}
public String account2Uuid(String userAccount){
    return this.IdAccount.get(userAccount);
}
public Boolean idOrAccountIsExist(String s){return  this.IdAccount.containsKey(s);}
public String uuidToAcount(String uuid){
    return this.IdAccount.get(uuid);
}
public void removeByIdOrAccount(String s){
    String value= this.IdAccount.get(s);
    this.IdAccount.remove(s);
    this.IdAccount.remove(value);
}



    public void mainService () throws IOException, InterruptedException {
        if(WD.equals("星期六")||WD.equals("星期日")){
            System.out.println("in weekend, we will use fake data");
            this.bid=1.03221f;
            this.ask=1.03240f;
            Map<String,Float> map0=new HashMap<>();
            map0.put("bid",this.bid);
            map0.put("ask",this.ask);


            while(true){
              Map<String,Float> map = sub.subService00(map0);
                this.bid=map.get("bid");
                this.ask=map.get("ask");
              Thread.sleep(2500);
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
                Map<String,Float> map = sub.subService01();
                this.bid=map.get("bid");
                this.ask=map.get("ask");
               Thread.sleep(2500);
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

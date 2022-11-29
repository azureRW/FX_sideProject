package model.deep;

import mappingObj.catchJsonFather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class semiPersistence  {
    private Date date =new Date();
    private Map<String,String> IDset = new HashMap<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    private String WD = sdf.format(date);
    private float bid;
    private float ask;
    private  Boolean b;
    @Autowired
    private catchJsonFather father;
    @Autowired
    private model.deep.metaAPI metaAPI;
//    @Autowired
//    unitTest unit;
    @Autowired
    subServiceOfSemi sub;


    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    private String userToken;

@Deprecated
    public Map<String,Float> subService00(Map<String, Float> map0) throws InterruptedException {
        this.bid=this.bid+0.0005f;
        this.ask=this.ask+0.0005f;
        System.out.println("bid="+this.bid+" ask="+this.ask);
        Map<String,Float> map=new HashMap<>();
        map.put("bid",this.bid);
        map.put("ask",this.ask);

                return map;
    }
    @Deprecated
    public Map<String, Float> subService01() throws InterruptedException, IOException {
        HashMap<String, Float> map = metaAPI.getCurrentPrice();
        System.out.println("bid="+map.get("bid") + " ask=" + map.get("ask"));
        this.bid = map.get("bid");
        this.ask = map.get("ask");
        Map<String,Float> map0=new HashMap<>();
        map.put("bid",this.bid);
        map.put("ask",this.ask);

                return map0;
    }

public void userTokerSet(String userAccount){
    this.IDset.put(userAccount,UUID.randomUUID().toString());
    this.IDset.put(IDset.get(userAccount),userAccount);
}
public String account2Uuid(String userAccount){
    return this.IDset.get(userAccount);
}
public Boolean idExist(String userAccount){
    return  this.IDset.containsKey(userAccount);
}
public String uuidToAcount(String uuid){
    return this.IDset.get(uuid);
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

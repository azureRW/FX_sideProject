package model.deep;

import model.dao.catchJsonFather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.AbstractRedisConnection;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCommand;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.persistence.PostLoad;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Optional.ofNullable;

@Component
public class semiPersistence  {
    @Autowired
    private RedisTemplate template;
    @Autowired
    private static Date date =new Date();
    private Map<String,String> IdAccount = new HashMap<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    private static String WD = sdf.format(date);
    private float bid;
    private float ask;
    private  Boolean b;
    private String mode;
    private Logger log = LoggerFactory.getLogger(semiPersistence.class);




    @Autowired
    private catchJsonFather father;
    @Autowired
    private model.service.metaAPI metaAPI;
//    @Autowired
//    unitTest unit;
    @Autowired
    subServiceOfSemi sub;
    @Bean
    private void initial(){

        Set<String> keys = template.keys("*");
        log.info(keys.toString());
        template.delete(keys);
    }

    public semiPersistence() {
        log.info("today is {}, have a nice day",this.WD);
    }


    public void userTokerSet(String userAccount){
        ValueOperations valueOperations = this.template.opsForValue();

//       this.IdAccount.put(userAccount,UUID.randomUUID().toString());
        valueOperations.set(userAccount,UUID.randomUUID().toString());
//       this.value.put(IdAccount.get(userAccount),userAccount);
        valueOperations.set(valueOperations.get(userAccount),
                userAccount);
}
public String account2Uuid(String userAccount){
    ValueOperations valueOperations = this.template.opsForValue();
//    return this.IdAccount.get(userAccount);
    return (String) valueOperations.get(userAccount);
}
public Boolean idOrAccountIsExist(String s){
    ValueOperations valueOperations = this.template.opsForValue();
//        return  this.IdAccount.containsKey(s);
        Optional<Object> o = ofNullable(valueOperations.get(s));
        return o.isPresent();
    }
public String uuidToAcount(String uuid){
//    return this.IdAccount.get(uuid);
    ValueOperations valueOperations = this.template.opsForValue();
    return (String) valueOperations.get(uuid);
}
public void removeByIdOrAccount(String s){
//    String value= this.IdAccount.get(s);
//    this.IdAccount.remove(s);
//    this.IdAccount.remove(value);
    ValueOperations valueOperations = this.template.opsForValue();
    String value = (String) valueOperations.get(s);
    this.template.delete(value);
    this.template.delete(s);


}



    public void newMainService() throws IOException, InterruptedException {
//    log.info("today is '{}', have a nice day",this.WD);
        if(WD.equals("星期六")||WD.equals("星期日")){
            fakeMode();
        }
        else {
            if (!metaAPI.isdeploy) {
                metaAPI.deployAccount();
                Thread.sleep(2500);
                while (!metaAPI.AccountStatusTest()) {
                    Thread.sleep(1000);
                }
                Thread.sleep(5000);}
            realMode();
        }
    }

    private void realMode() throws IOException, InterruptedException {
        Map<String, Float> map  = sub.subService01();
        this.ask=map.get("ask");
        this.bid=map.get("bid");
    }

    public void fakeMode() throws InterruptedException {
//    log.info("how can u get into this?");
    Map<String,Float> map= sub.subService00();
        this.ask=map.get("ask");
        this.bid=map.get("bid");
    }




    public void mainService () throws IOException, InterruptedException {
        if(WD.equals("星期六")||WD.equals("星期日")){
//            System.out.println("in weekend, we will use fake data");
            log.info("周末外匯市場不開市，將使用假數據");
            while(true){
              Map<String,Float> map = sub.subService00();
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
    public String getMode() {
        return mode;
    }



}

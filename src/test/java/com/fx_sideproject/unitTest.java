package com.fx_sideproject;

import config.redisConfig;
import lombok.Data;
import model.dao.jpaEntranceForTradeData;
import model.dao.jpaEntranceForUsers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = redisConfig.class)
public class unitTest {

//    @Autowired
//    userBehavior behavior;



    //find user
//        public void test(){
//            Iterable<tradeUser> users = inf.findAll();
//            for(tradeUser user : users){
//                System.out.println(user.getUserAccount());
//            }
    //find user2
//    public void test(){
//        Optional<tradeUser> user = inf.findById(2L);
////        System.out.println(user.get().getUserAccount());
//        if(user.isPresent()) System.out.println(user.get().getUserAccount());
//        else System.out.println("cant find any user whose if = 2");
//    }
    //    Creat user
//    public void test(){
//        tradeUser u1 = new tradeUser();
//        u1.setUserAccount("eirc1111");
//        u1.setUserProperty(9999);
//        u1.setUserPassword("000");
//        inf.save(u1);
//    }
    //Delete user
//    public void test(){
//        inf.deleteById(1L);
//    }
    //Set user
//    public void test(){
//        tradeUser u1 = new tradeUser(
//                14L,
//                "ericCgange",
//                "1234",
//                99999
//        );
//        inf.save(u1);
//    }
    //New test1
//    public  void test(String S){
//        tradeUser u1 = inf.findByUserAccount(S);
//        System.out.println(u1.get());
//    }
//    public void test() throws ParseException {
//        tradeRecode recode = new tradeRecode();
//        Date date =new Date();
//        String mydate = "1997-12-18+0800";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddZ");
//        recode.setTradetime(sdf.parse(mydate));
//        DATA.save(recode);
//
//    }
    // try save several trade data to user's information
//    public void test() throws InterruptedException {
//        tradeUser user = inf.findById(1L).get();
//        List<tradeRecode> list =new ArrayList<>();
//        for(int i =1;i<11;i++){
//            tradeRecode recode = new tradeRecode();
//            recode.setType("test"+i);
//            recode.setTradetime(new Date());
//            list.add(recode);
//        }
//        user.setTradeRecodeList(list);
//        inf.save(user);
//            }
    //test register method
//    public void test(){
//        System.out.println(behavior.regitser("Hiserber","0000"));
//    }
    //test login
//    public void test(){
//        System.out.println((behavior.login("Eric5","1234")));
//    }
    //test EEEE
//    public void test() throws ParseException {
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        Date date1 = sdf1.parse("2022-11-20");
//        System.out.println(sdf.format(date));
//        System.out.println(sdf.format(date1));
//    }
    @Autowired
    RedisTemplate template;
    @Test
public  void test(){
        ValueOperations valueOperations = template.opsForValue();
        valueOperations.set("安安","安安安安");
        System.out.println(valueOperations.get("安安"));
}

}

package model;

import lombok.Data;
import mappingObj.jpaEntranceForTradeData;
import mappingObj.jpaEntranceForUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;


@Controller
@Data
public class unitTest {
    @Autowired
    jpaEntranceForUsers inf;
    @Autowired
    jpaEntranceForTradeData DATA;
//    @Autowired
//    userBehavior behavior;
    String test;


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
    @EventListener
    public  void test(unitTest2 unit2){
        System.out.println("test");
    }

}

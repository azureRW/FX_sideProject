package controller;

import model.tradeUser;
import model.jpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;


@Controller
public class unitTest {
    @Autowired
    jpaTest inf;

    //find user
//        public void test(){
//            Iterable<tradeUser> users = inf.findAll();
//            for(tradeUser user : users){
//                System.out.println(user.getUserAccount());
//            }
    //Creat user
//    public void test(){
//        tradeUser u1 = new tradeUser();
//        u1.setUserAccount("eirc1111");
//        u1.setUserProperty(9999);
//        u1.setUserPassword("000");
//        inf.save(u1);
//    }
}

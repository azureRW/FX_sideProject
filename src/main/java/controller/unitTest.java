package controller;

import model.tradeUser;
import model.jpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class unitTest {
    @Autowired
    jpaTest inf;
//        tradeUser u1 = inf.findById(1L).get();
//        public void test(){
//            System.out.println(u1.getUserAccount());
//    }
    public void test(){
        tradeUser u1 = new tradeUser();
        u1.setUserAccount("eirc1111");
        u1.setUserProperty(9999);
        u1.setUserPassword("000");
        inf.save(u1);
    }
}

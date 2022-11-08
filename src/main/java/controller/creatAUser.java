package controller;

import model.jpaTest;
import model.tradeUser;

public class creatAUser {
    jpaTest jpaT;
    public void t1(){
        System.out.println(jpaT.findById(1L).get());
    }
    public void t2(){
        tradeUser u1 = new tradeUser();
        u1.setUserAccount("eric234");
        u1.setUserPassword("000");
        u1.setUserProperty(99999);
        jpaT.save(u1);
    }
}

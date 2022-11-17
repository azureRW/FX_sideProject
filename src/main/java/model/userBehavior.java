package model;

import mappingObj.jpaEntranceForUsers;
import mappingObj.tradeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class userBehavior {
    @Autowired
    jpaEntranceForUsers entrance;
    @Autowired
    semiPersistence persistence;
    tradeUser userL;
public String regitser(String account,String password){
    if(!entrance.existsByUserAccount(account)) {
        tradeUser user = new tradeUser();
        user.setUserProperty(100000);
        user.setUserPassword(password);
        user.setUserAccount(account);
        System.out.println("Main KEY is " + entrance.save(user).getId());
        return "successful";
    }
    else return "failed ! account name has existed !";
}
public String login(String account,String password){
    if(entrance.existsByUserAccount(account)){
        if(entrance.findByUserAccount(account).getUserPassword().equals(password)) {
            this.userL = entrance.findByUserAccountAndUserPassword(account,password);
            return "successful";
        }
        else return "password is wrong";
    }
    else return "account does not exist";
}
    public String sell(int unit){

//i have to manipulate db here to deduct user's caution money and save a trade recode to db
//ask(賣出) and bid(買進) are at the point of financial institution, it means bid will be the price we sell to market and vice versa
        Optional<tradeUser> op = Optional.ofNullable(this.userL);
        System.out.println(op.isEmpty());
        if(op.isEmpty()) return "not login";
        this.userL.setUserProperty(this.userL.getUserProperty()-unit*100000/100*this.persistence.getBid());
        entrance.save(this.userL);
        return "trade finish, ";

    }
    public String buy(){
        return "test buy";
    }
}

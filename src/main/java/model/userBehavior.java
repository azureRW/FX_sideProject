package model;

import mappingObj.jpaEntranceForTradeData;
import mappingObj.jpaEntranceForUsers;
import mappingObj.tradeUser;
import mappingObj.userRecode;
import model.deep.semiPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
public class userBehavior {
    @Autowired
    jpaEntranceForUsers entrance;
    @Autowired
    private jpaEntranceForTradeData dataEntrance;
    @Autowired
    private semiPersistence persistence;
    private tradeUser userL;

    String type;
public String register(String account, String password){
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
        float bidPrice= this.persistence.getBid();
        this.userL.setUserProperty(this.userL.getUserProperty()-unit*100000/100*bidPrice);
        userRecode recode = new userRecode();
        recode.setOuterJoin(userL.getId());
        recode.setUnit(-1*unit);
        recode.setType("sell");
        recode.setTime(new Date());
        recode.setPrice(bidPrice);
        recode.setOffset(false);
        dataEntrance.save(recode);
        entrance.save(this.userL);


        type="sell";
        return "sell trade finish, sell "+unit*100000+" EUROS at "+bidPrice ;

    }
    public String buy(int unit){
        Optional<tradeUser> op = Optional.ofNullable(this.userL);
        System.out.println(op.isEmpty());
        if(op.isEmpty()) return "not login";
        float askPrice = persistence.getAsk();
        this.userL.setUserProperty(this.userL.getUserProperty()-unit*100000/100*askPrice);
        userRecode recode = new userRecode();
        recode.setOuterJoin(userL.getId());
        recode.setUnit(unit);
        recode.setType("buy");
        recode.setTime(new Date());
        recode.setPrice(askPrice);
        recode.setOffset(false);
        dataEntrance.save(recode);
        entrance.save(this.userL);
        type="buy";
        return "buy trade finish, buy "+unit*100000+" EUROS at "+askPrice ;
    }
    public void test(){
    this.userL.setUserProperty(this.userL.getUserProperty()-0.05);
    entrance.save(userL);
    }
    public String offset() {
        Optional<tradeUser> op = Optional.ofNullable(this.userL);
        System.out.println(op.isEmpty());
        if (op.isEmpty()) return "not login";
        ArrayList<userRecode> list = dataEntrance.findByOuterJoinAndOffsetIsFalse(this.userL.getId());
//        System.out.println("find");
//        String s = new String();
//        for (int i = 0; i < list.size(); i++) {
//            s = s + list.get(i).get()+"\n";
//        }
        for(int i=0;i<list.size();i++){
            userRecode re = list.get(i);
            re.setOffset(true);
            if(re.getType().equals("sell")){
                float nowprice= persistence.getAsk();
                double gain = (
                        100000*re.getUnit()*(  nowprice-re.getPrice()  )//points differe
                );
                double margin = 100000/100*re.getPrice();
                userL.setUserProperty(userL.getUserProperty()+gain+margin);
                list.get(i).setOffset(true);
                list.get(i).setOffsetPrice(nowprice);
                list.get(i).setGain(gain);
            }
            else{
                float nowprice = persistence.getBid();
                double gain =(
                        100000*re.getUnit()*(  nowprice-re.getPrice()  )
                        );
                double margin = 100000/100*re.getPrice();
                userL.setUserProperty(userL.getUserProperty()+gain+margin);
                list.get(i).setOffset(true);
                list.get(i).setOffsetPrice(nowprice);
                list.get(i).setGain(gain);
            }
            dataEntrance.saveAll(list);
            entrance.save(userL);
        }
        return "offset";
    }
    public void logout(){

    }
}

package model.service;

import model.forWebsocket.message;
import model.dao.jpaEntranceForTradeData;
import model.dao.jpaEntranceForUsers;
import model.deep.semiPersistence;
import model.PO.tradeUser;
import model.PO.userRecode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class userBehavior {
    @Autowired
    jpaEntranceForUsers entrance;
    @Autowired
    private jpaEntranceForTradeData dataEntrance;
    @Autowired
    private semiPersistence semi;
    Logger log = LoggerFactory.getLogger(userBehavior.class);


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
//public String login(String account,String password){
//    if(entrance.existsByUserAccount(account)){
//        if(entrance.findByUserAccount(account).getUserPassword().equals(password)) {
//            tradeUser userL = entrance.findByUserAccountAndUserPassword(account,password);
//
//            return "successes and serverToken is "+ token.getToken();
//        }
//        else return "password is wrong";
//    }
//    else return "account does not exist";
//}
@CacheEvict(cacheNames = "history",allEntries = true)
public String userXsell(String user,int unit){

//i have to manipulate db here to deduct user's caution and save a trade recode to db
//ask(賣出價) and bid(買進價) are at the point of financial institution, it means bid will be the price we(user) sell to market and vice versa
        Optional<tradeUser> op = Optional.ofNullable(entrance.findByUserAccount(user));
        System.out.println(op.isEmpty());
        if(op.isEmpty()) return "not login";
        tradeUser userL=op.get();
        float bidPrice= this.semi.getBid();
        userL.setUserProperty(userL.getUserProperty()-unit*100000/100*bidPrice);
        userRecode recode = new userRecode();
        recode.setOuterJoin(userL.getId());
        recode.setUnit(-1*unit);
        recode.setType("sell");
        recode.setTime(new Date());
        recode.setPrice(bidPrice);
        recode.setOffset(false);
        dataEntrance.save(recode);
        entrance.save(userL);


        type="sell";
        return "sell trade finish, sell "+unit*100000+" EUROS at "+bidPrice ;
    }
    @CacheEvict(cacheNames = "history",allEntries = true)
    public String userXbuy(String user,int unit){
        Optional<tradeUser> op = Optional.ofNullable(entrance.findByUserAccount(user));
        System.out.println(op.isEmpty());
        if(op.isEmpty()) return "not login";
        tradeUser userL=op.get();
        float askPrice = semi.getAsk();
        userL.setUserProperty(userL.getUserProperty()-unit*100000/100*askPrice);
        userRecode recode = new userRecode();
        recode.setOuterJoin(userL.getId());
        recode.setUnit(unit);
        recode.setType("buy");
        recode.setTime(new Date());
        recode.setPrice(askPrice);
        recode.setOffset(false);
        dataEntrance.save(recode);
        entrance.save(userL);
        type="buy";
        return "buy trade finish, buy "+unit*100000+" EUROS at "+askPrice ;
    }
//    public void test(){
//    this.userL.setUserProperty(this.userL.getUserProperty()-0.05);
//    entrance.save(userL);
//    }
    @CacheEvict(cacheNames = "history",allEntries = true)
    public String userXoffset(String user) {
        Optional<tradeUser> op = Optional.ofNullable(entrance.findByUserAccount(user));
        tradeUser userL=op.get();
        Double ttg=0d;
        System.out.println(op.isEmpty());
        if (op.isEmpty()) return "not login";
        ArrayList<userRecode> list = dataEntrance.findByOuterJoinAndOffsetIsFalse(userL.getId());
//        System.out.println("find");
//        String s = new String();
//        for (int i = 0; i < list.size(); i++) {
//            s = s + list.get(i).get()+"\n";
//        }
        if(list.size()!=0) {
            for (int i = 0; i < list.size(); i++) {
                userRecode re = list.get(i);
                re.setOffset(true);
                if (re.getType().equals("sell")) {
                    float nowprice = semi.getAsk();
                    double gain = (
                            100000 * re.getUnit() * (nowprice - re.getPrice())//points differe
                    );
                    ttg = ttg + gain;
                    double margin = 100000 / 100 * re.getPrice();
                    userL.setUserProperty(userL.getUserProperty() + gain + margin);
                    list.get(i).setOffset(true);
                    list.get(i).setOffsetPrice(nowprice);
                    list.get(i).setGain(gain);
                } else {
                    float nowprice = semi.getBid();
                    double gain = (
                            100000 * re.getUnit() * (nowprice - re.getPrice())
                    );
                    ttg = ttg + gain;
                    double margin = 100000 / 100 * re.getPrice();
                    userL.setUserProperty(userL.getUserProperty() + gain + margin);
                    list.get(i).setOffset(true);
                    list.get(i).setOffsetPrice(nowprice);
                    list.get(i).setGain(gain);
                }

            }
            DecimalFormat   fnum   =   new DecimalFormat("##0.00");
            ttg= Double.valueOf(fnum.format(ttg));
            dataEntrance.saveAll(list);
            entrance.save(userL);
            return "offset! total gain is " + ttg;
        }
        else return "non deal to offset";
    }
    @Cacheable(cacheNames = "history",key = "#p0")
    public List<userRecode> history(String userAccount){
        log.info("{} access history",userAccount);
        return dataEntrance.findByOuterJoin(
                (entrance.findByUserAccount(userAccount)).getId());
    }
    public Boolean checkUserPropertyForBuy(String id){
        String userAccount = semi.uuidToAcount(id);
        float price = semi.getAsk();
        double Property = entrance.findByUserAccount(userAccount).getUserProperty();
        if(Property<((price*100000/100)+100)) return true;
        else return false;
    }
    public Boolean checkUserPropertyFroSell(String id){
        String userAccount = semi.uuidToAcount(id);
        float price = semi.getBid();
        double Property = entrance.findByUserAccount(userAccount).getUserProperty();
        if(Property<((price*100000/100)+100)) return true;
        else return false;
    }
    public void userXtest(String Account){

    }

    public void logout(String id){
        semi.removeByIdOrAccount(id);
    }
}

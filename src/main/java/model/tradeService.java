package model;

import mappingObj.tradeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class tradeService {
    @Autowired
    metaAPI meta;
    //this is a trade trader buy n standard hands with 100X lever
    public void buyStandHand(int hand, tradeUser user){

    }

}

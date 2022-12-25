package controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import model.PO.tradeUser;
import model.PO.userRecode;
import model.dao.jpaEntranceForTradeData;
import model.dao.jpaEntranceForUsers;
import model.service.metaAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/fakeTerminal")
public class fake {
    @Autowired
    private metaAPI meta;
    @Autowired
    private jpaEntranceForUsers entrance;
    @Autowired
    private jpaEntranceForTradeData data;

@Cacheable(cacheNames = "test",key = "#p0")
  @GetMapping("/{account}")
  public List<userRecode> RUready(@PathVariable String account){
    return data.findByOuterJoin((entrance.findByUserAccount(account)).getId());
    }
    public void test(){
        System.out.println();
    }
    @GetMapping("/askconnect")
    public boolean testConnection() throws IOException {
        return     meta.AccountStatusTest();
    }
    @GetMapping("/connect")
    public String deploy() throws IOException {
      meta.deployAccount();
      return "system is deployed";
    }
    @GetMapping("/test")
    public String testForCandle() throws IOException, ParseException {
//      meta.getCurrentCandle();
      return "test";
    }
    @GetMapping("/time")
    public String getTime() throws JsonProcessingException, ParseException {
      return meta.getTime().toString();
    }



}



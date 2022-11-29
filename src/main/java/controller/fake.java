package controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import model.deep.metaAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;


//@RestController
//@RequestMapping("/fakeTerminal")
public class fake {
    @Autowired
    private metaAPI meta;


  @GetMapping("/{id}")
  public String RUready(@PathVariable int id){
        System.out.println(id);
        return "hello";
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
      meta.getCurrentCandle();
      return "test";
    }
    @GetMapping("/time")
    public String getTime() throws JsonProcessingException, ParseException {
      return meta.getTime().toString();
    }



}



package controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/fakeTerminal")
public class fake {
    @Autowired
    private metaAPItest meta;

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




}



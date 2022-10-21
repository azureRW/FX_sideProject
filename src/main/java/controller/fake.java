package controller;


import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/fakeTerminal")
public class fake {


  @GetMapping("/{id}")
  public String RUready(@PathVariable int id){
        System.out.println(id);
        return "hello";
    }
    public void test(){
      System.out.println();
    }
}


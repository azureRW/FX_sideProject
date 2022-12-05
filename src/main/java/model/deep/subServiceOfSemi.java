package model.deep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Component
public class subServiceOfSemi {
    @Autowired
    model.service.metaAPI metaAPI;
     public Map<String,Float> subService00(Map<String,Float> map) throws InterruptedException {
        map.put("bid",map.get("bid")+0.005f);
        map.put("ask",map.get("ask")+0.005f);
        System.out.println("bid="+map.get("bid")+" ask="+map.get("ask"));
                return map;
    }
    public Map<String, Float> subService01() throws InterruptedException, IOException {
        HashMap<String, Float> map = metaAPI.getCurrentPrice();
        System.out.println("bid="+map.get("bid") + " ask=" + map.get("ask"));

                return map;
    }

}

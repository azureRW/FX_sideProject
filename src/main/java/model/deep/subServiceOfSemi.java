package model.deep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Component
public class subServiceOfSemi {
    Float bid=1.03221f;
    Float ask=1.03240f;
    @Autowired
    model.service.metaAPI metaAPI;
     public Map<String,Float> subService00() throws InterruptedException {
         Map<String,Float> map = new HashMap<>();
         map.put("bid",bid+=0.005f);
         map.put("ask",ask+=0.005f);
                return map;
    }
    public Map<String, Float> subService01() throws InterruptedException, IOException {
        HashMap<String, Float> map = metaAPI.getCurrentPrice();
        System.out.println("bid="+map.get("bid") + " ask=" + map.get("ask"));

                return map;
    }

}

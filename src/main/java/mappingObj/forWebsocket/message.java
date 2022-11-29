package mappingObj.forWebsocket;

import lombok.Data;
import mappingObj.userRecode;

import java.util.ArrayList;
import java.util.List;

@Data
public class message {
    private String content;
    private List<userRecode> extra ;


    public message(String s) {
        this.content= s;
    }
    public message(){}
}

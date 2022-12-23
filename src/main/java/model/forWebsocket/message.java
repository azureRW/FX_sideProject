package model.forWebsocket;

import lombok.Data;
import model.PO.userRecode;

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

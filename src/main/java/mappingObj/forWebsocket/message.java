package mappingObj.forWebsocket;

import lombok.Data;

@Data
public class message {
    private String content;

    public message(String s) {
        this.content=s;
    }
    public message(){}
}

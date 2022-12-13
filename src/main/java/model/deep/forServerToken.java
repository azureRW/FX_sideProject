package model.deep;

import org.springframework.stereotype.Component;

import java.util.UUID;

//@Component
public class forServerToken {
    public String Token = UUID.randomUUID().toString();

    public forServerToken() {
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}

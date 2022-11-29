package model.deep;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
@Controller
public class kickAwayErrorHandler implements ResponseErrorHandler {


    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
    }
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
    }
}

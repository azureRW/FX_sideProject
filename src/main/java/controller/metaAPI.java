package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import cloud.metaapi.sdk.meta_api.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import model.currentPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class metaAPI {
    private boolean EnvironmentIsSet = false;
    private final String URL = "https://mt-client-api-v1.new-york.agiliumtrade.ai";
    @Value("${pathFOrCandle}")
    private String currentCandle;
    @Value("${accountId}")
    private String accountId;
    @Value("${token}")
    private String token;



    public void setApi(MetaApi api) {
        this.api = api;
    }

    public void setAccount(MetatraderAccount account) {
        this.account = account;
    }

    MetaApi api;
    MetatraderAccount account;

    private String path;
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    ObjectMapper objectMapper = new ObjectMapper();
    private void setEnvironment() throws IOException {
        EnvironmentIsSet = true;
//        setApi(new MetaApi(token));
//        setAccount(api.getMetatraderAccountApi().getAccount(accountId).join());
        addHeader();
        path ="/users/current/accounts/"+accountId+"/symbols/EURUSD/current-price?keepSubscription=true";
    }


    public void testForMetaApi(){
    }
    @Deprecated
    public void redeployMtAptAccount() throws IOException {
        if (!EnvironmentIsSet) setEnvironment();
        account.redeploy().join();
        System.out.println("redeploy signal is lunched");
    }
    @Deprecated
    public void testSystem() throws InterruptedException, IOException {
        if (!EnvironmentIsSet) setEnvironment();
        String status = account.getConnectionStatus().toString();
        System.out.println("system is "+status);
        if (status!="CONNECTED") {
            System.out.println("wait some seconds for system to redeploy..");
            redeployMtAptAccount();
            while(status!="CONNECTED"){
                Thread.sleep(2000);
                System.out.println("wait some second...");
                status=account.getConnectionStatus().toString();
            }
            System.out.println("system deploy");
        }

    }
    private void addHeader(){
        this.headers.add("auth-token",this.token);
    }
    public ResponseEntity<String> getPriceJSON(){
        ResponseEntity<String> res = restTemplate.exchange(this.URL+this.path
                , HttpMethod.GET
                ,new HttpEntity<String>(this.headers)
                ,String.class);

        return res;
    }
    public void getCurrentPriceFromSever() throws InterruptedException, IOException {
        if (!EnvironmentIsSet) setEnvironment();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        currentPrice price = objectMapper.readValue(getPriceJSON().getBody(),currentPrice.class);
        System.out.println(price.bid.toString());
    }

    //if account doesn't deploy, the function will deploy it and return null
    //if account is deployed it return null
    //if it is in 15 minutes CD time, is will sout corresponding erro message
    public void deployAccount() throws IOException {
        if (!EnvironmentIsSet) setEnvironment();
        if(!AccountStatusTest())//if account doesn't be deployed, I will run
        {
            restTemplate.setErrorHandler(new fuckOfErrorHandler());
            ResponseEntity<String> response= restTemplate.postForEntity(
                    "https://mt-provisioning-api-v1.agiliumtrade.agiliumtrade.ai/users/current/accounts/"+accountId+"/deploy?executeForAllReplicas=true"
                    ,new HttpEntity<String>(null, headers)
                    , String.class);
            System.out.println(response.getBody());
        }
        else System.out.println("deployAccount does not run cuz account is deployed");
    }
    // the function will return the status of metaApiServer, true means connect ; false means disconnect
    public boolean AccountStatusTest() throws IOException {
        if (!EnvironmentIsSet) setEnvironment();
        ResponseEntity<String> response =restTemplate.exchange(
                "https://mt-provisioning-api-v1.agiliumtrade.agiliumtrade.ai/users/current/accounts/"+accountId,
                HttpMethod.GET
                ,new HttpEntity<String>(this.headers)
                ,String.class);
//             System.out.println(response.getBody());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HashMap<String,String> e = objectMapper.readValue(response.getBody(),HashMap.class);
        System.out.println("account is "+e.get("connectionStatus"));
        return e.get("connectionStatus").equals("CONNECTED");
    }
    public String getCurrentCandle() throws IOException {
        if (!EnvironmentIsSet) setEnvironment();
        ResponseEntity<String> response = restTemplate.exchange(
                currentCandle
                ,HttpMethod.GET
                ,new HttpEntity<String>(this.headers)
                ,String.class
        );
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        HashMap<String,String> e =objectMapper.readValue(response.getBody(),HashMap.class);
        System.out.println(e.entrySet());
        return e.entrySet().toString();
    }
    public metaAPI() throws ExecutionException, InterruptedException, IOException {

    }
}

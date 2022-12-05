package model.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import model.dao.catchJson;
import model.dao.catchJsonFather;
import model.dao.currentPrice;
import model.deep.kickAwayErrorHandler;
import model.dao.serverTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class metaAPI {
    private Logger log = LoggerFactory.getLogger(metaAPI.class);
    private boolean EnvironmentIsSet = false;
    @Value("${pathFOrCandle}")
    private String currentCandle;
    @Value("${accountId}")
    private String accountId;
    @Value("${token}")
    private String token;
    public Boolean isdeploy= false;
    private String path;
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    ObjectMapper objectMapper = new ObjectMapper();
    private void setEnvironment() {
        EnvironmentIsSet = true;
        addHeader();
        path ="/users/current/accounts/"+accountId+"/symbols/EURUSD/current-price?keepSubscription=true";
    }

    private void addHeader(){
        this.headers.add("auth-token",this.token);
    }
    public ResponseEntity<String> getPriceJSON(){
        String URL = "https://mt-client-api-v1.new-york.agiliumtrade.ai";
        ResponseEntity<String> res = restTemplate.exchange(URL +this.path
                , HttpMethod.GET
                ,new HttpEntity<String>(this.headers)
                ,String.class);
        //print info. from metaApi directly
//        System.out.println(res.getBody());
        return res;
    }
       public HashMap<String, Float> getCurrentPrice() throws InterruptedException, IOException {
        if (!EnvironmentIsSet) setEnvironment();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        currentPrice price = objectMapper.readValue(getPriceJSON().getBody(),currentPrice.class);
//        System.out.println(price.bid.toString());
        HashMap<String,Float> map = new HashMap<>();
        map.put("bid",price.bid);
        map.put("ask",price.ask);
        return map;
    }


    //if account doesn't deploy, the function will deploy it and return null
    //if account is deployed it return null
    //if it is in 15 minutes CD time, is will sout corresponding erro message
    public void deployAccount() throws IOException {
        if (!EnvironmentIsSet) setEnvironment();
        if(!AccountStatusTest())//if account doesn't be deployed, I will run
        {
            restTemplate.setErrorHandler(new kickAwayErrorHandler());
            ResponseEntity<String> response= restTemplate.postForEntity(
                    "https://mt-provisioning-api-v1.agiliumtrade.agiliumtrade.ai/users/current/accounts/"+accountId+"/deploy?executeForAllReplicas=true"
                    ,new HttpEntity<String>(null, headers)
                    , String.class);
            log.info(response.getBody());
        }
        else log.info("deployAccount does not run cuz account is deployed");
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
        log.info("account is '{}'",e.get("connectionStatus"));
        this.isdeploy = e.get("connectionStatus").equals("CONNECTED");
        return this.isdeploy;
    }
    public void getCurrentCandle() throws IOException, ParseException {
        if (!EnvironmentIsSet) setEnvironment();
        ResponseEntity<String> response = restTemplate.exchange(
                currentCandle
                ,HttpMethod.GET
                ,new HttpEntity<String>(this.headers)
                ,String.class
        );
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        catchJson cj =objectMapper.readValue(response.getBody(),catchJson.class);

        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

        sdfInput.setTimeZone(TimeZone.getTimeZone("GMT"));
        sdfOutput.setTimeZone(TimeZone.getTimeZone("GMT"));
        cj.date=sdfOutput.format(sdfInput.parse(cj.date));
        log.info(cj.date);
    }
    public ArrayList<catchJson> getHistoryCandle() throws ParseException, JsonProcessingException {
        Date date = getTime();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MINUTE,-121);
        Date date1 =cal.getTime();
        cal.add(Calendar.MINUTE,+120);
        Date date2 =cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String url ="https://marketdata.tradermade.com/api/v1/timeseries?" +
                "currency=EURUSD" +
                "&api_key=EPsEBf-ZxkQW0vClUTMo" +
                "&format=records";
        String startDate= sdf.format(date1);
        String endDate=sdf.format(date2);
        String interval="minute";
        url=url+"&start_date="+startDate
                +"&end_date="+endDate
                +"&interval="+interval;
        catchJsonFather e= objectMapper.readValue(restTemplate.getForObject(url,String.class),catchJsonFather.class);
        ArrayList<catchJson> e_son = e.quotes;// the list contain all history candles
        return e_son;
    }
    @Deprecated
    public void catchCandle_test() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        catchJsonFather e= objectMapper.readValue(new File("C:\\code\\java\\fx\\FX_sideProject\\test.json"),catchJsonFather.class);
        ArrayList<catchJson> e_son = e.quotes;
    }

    public Date getTime() throws JsonProcessingException, ParseException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        if (!EnvironmentIsSet) setEnvironment();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://mt-client-api-v1.new-york.agiliumtrade.ai/users/current/accounts/0625d03d-faae-4b80-8dd5-4df3ebd56f83/server-time"
                ,HttpMethod.GET
                ,new HttpEntity<String>(this.headers)
                ,String.class
        );
        serverTime st = objectMapper.readValue(response.getBody(),serverTime.class);
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdfInput.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = sdfInput.parse(st.time);
        log.info("'{}'",date);
        return date;

    }
    public metaAPI() throws ExecutionException, InterruptedException, IOException {

    }
}

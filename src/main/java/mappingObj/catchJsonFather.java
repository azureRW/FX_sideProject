package mappingObj;


import org.springframework.stereotype.Controller;

import java.util.ArrayList;
@Controller
public class catchJsonFather {
  public String base_currency;
  public String end_date;
  public String endpoint;
  public String quote_currency;
  public ArrayList<catchJson> quotes;
  public String request_time;
  public String start_date;
}

package model.DTO;


import model.DTO.catchJson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class catchJsonFather {
  public String base_currency;
  public String end_date;
  public String endpoint;
  public String quote_currency;
  public ArrayList<catchJson> quotes;
  public String request_time;
  public String start_date;
}

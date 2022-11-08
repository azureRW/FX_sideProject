package model;


import com.fasterxml.jackson.annotation.JsonAlias;

public class catchJson {
   @JsonAlias("time")
   public String date;
   public  float close;
   public  float high;
   public  float low;
   public  float open;
}

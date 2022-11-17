package mappingObj;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

public class candleSet {

    public  float close;
    public String date;
    public  float heigh;
    public  float low;
    public  float open;

    @Entity
    @Data
    public static class tradeRecode {
        public tradeRecode() {
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", nullable = false)
        private Long id;
        private Long outerKey;
        private String type;
        private float price;
        private int unit;
        private int profit_and_loos;
        Date tradetime;
        public tradeRecode(String type){
            this.type=type;
        }
        //buy or sell
        //volume
        //price
    }
}

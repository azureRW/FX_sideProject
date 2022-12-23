package model.PO;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class userRecode {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long outerJoin;
    private String type;
    private int unit;
    private float price;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    private Boolean offset;
    private double offsetPrice;
    private double gain;
    public String get(){
        return "id="+id+
                "type="+ type+
                "price="+ price+
                "unit="+ unit+
                "time=" + time;
    }

    public userRecode() {
    }

    public static class candleSet {

        public  float close;
        public String date;
        public  float heigh;
        public  float low;
        public  float open;


    }
}

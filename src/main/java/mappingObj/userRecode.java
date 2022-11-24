package mappingObj;

import lombok.Data;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.data.annotation.Id;

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
}

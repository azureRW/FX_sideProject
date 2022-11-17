package mappingObj;


import lombok.Data;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "trade_user")
@Controller
public class tradeUser {
    public tradeUser() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account")
    private String userAccount;
    @Column(name = "password")
    private String userPassword;
    @Column(name = "property")
    private double userProperty;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="outerKey")
    private List<candleSet.tradeRecode> tradeRecodeList;



}

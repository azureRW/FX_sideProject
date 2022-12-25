package model.PO;


import lombok.Data;
import model.PO.userRecode;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "trade_user")
@Component
public class tradeUser implements Serializable {
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
    private String SessionID;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "outerJoin")
    private List<userRecode> tradeRecodeList;



}

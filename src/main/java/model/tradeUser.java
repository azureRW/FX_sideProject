package model;


import javax.persistence.*;
@Entity
@Table(name = "trade_user")
public class tradeUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account")
    private String userAccount;
    @Column(name = "password")
    private String userPassword;
    @Column(name = "property")
    private int userProperty;

    public Long getId() {
        return id;
    }
    public  tradeUser(){}
    public tradeUser(Long id, String userAccount, String userPassword, int userProperty) {
        this.id = id;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userProperty = userProperty;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(int userProperty) {
        this.userProperty = userProperty;
    }
}

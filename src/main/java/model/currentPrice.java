package model;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
@Component
public class currentPrice {
    public String equity =null;
    public String symbol = null;

    public Float bid = null;

    public Float ask = null;

    public BigDecimal profitTickValue = null;

    public BigDecimal lossTickValue = null;

    public BigDecimal accountCurrencyExchangeRate = null;

    public String time = null;

    public String brokerTime = null;

    public currentPrice() {
    }

    public currentPrice(String equity, String symbol, Float bid, Float ask, BigDecimal profitTickValue, BigDecimal lossTickValue, BigDecimal accountCurrencyExchangeRate, String time, String brokerTime) {
        this.equity = equity;
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
        this.profitTickValue = profitTickValue;
        this.lossTickValue = lossTickValue;
        this.accountCurrencyExchangeRate = accountCurrencyExchangeRate;
        this.time = time;
        this.brokerTime = brokerTime;
    }
}

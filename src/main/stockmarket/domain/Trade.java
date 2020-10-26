package main.stockmarket.domain;

import java.util.Date;

public class Trade {

    private Date timestamp;
    private int shareQuantity;
    private TradeType tradeTypeIndicator;
    private Double tradedPrice;

    public Trade(int shareQuantity, TradeType tradeTypeIndicator, Double tradedPrice) {
        this.timestamp = new Date();
        this.shareQuantity = shareQuantity;
        this.tradeTypeIndicator = tradeTypeIndicator;
        this.tradedPrice = tradedPrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getShareQuantity() {
        return shareQuantity;
    }

    public void setShareQuantity(int shareQuantity) {
        this.shareQuantity = shareQuantity;
    }

    public TradeType getTradeTypeIndicator() {
        return tradeTypeIndicator;
    }

    public void setTradeTypeIndicator(TradeType tradeTypeIndicator) {
        this.tradeTypeIndicator = tradeTypeIndicator;
    }

    public Double getTradedPrice() {
        return tradedPrice;
    }

    public void setTradedPrice(Double tradedPrice) {
        this.tradedPrice = tradedPrice;
    }
}

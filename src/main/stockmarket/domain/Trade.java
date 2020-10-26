package main.stockmarket.domain;

import java.time.LocalDateTime;
import java.util.Date;

public class Trade {

    private LocalDateTime timestamp;
    private int shareQuantity;
    private TradeType tradeTypeIndicator;
    private Double tradedPrice;

    public Trade(int shareQuantity, TradeType tradeTypeIndicator, Double tradedPrice) {
        this.timestamp = LocalDateTime.now();
        this.shareQuantity = shareQuantity;
        this.tradeTypeIndicator = tradeTypeIndicator;
        this.tradedPrice = tradedPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
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

package main.stockmarket;

import main.stockmarket.domain.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StockMarket {

    public static final String EMPTY_STRING = "";
    public static final String ISSUE_WITH_NUMBER_TRY_AGAIN = "Issue with number, try again";
    List<Stock> stocks = new ArrayList<>();
    List<Trade> trades = new ArrayList<>();

    public StockMarket() {
        createStocks();
        getUserInput();
        System.out.println("GBCE All Share Index for all stocks: " + getGeometricMean());
    }

    private void createStocks() {
        Stock stock1 = new Stock(StockSymbol.TEA, StockType.COMMON, 0.0, null, 1.00);
        Stock stock2 = new Stock(StockSymbol.POP, StockType.COMMON, 0.08, null, 1.00);
        Stock stock3 = new Stock(StockSymbol.ALE, StockType.COMMON, 0.23, null, 0.60);
        Stock stock4 = new Stock(StockSymbol.GIN, StockType.PREFERRED, 0.08, 0.02, 1.00);
        Stock stock5 = new Stock(StockSymbol.JOE, StockType.COMMON, 0.13, null, 2.50);
        setStocks(stock1, stock2, stock3, stock4, stock5);
    }

    private void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        for (Stock stock : stocks) {
            while (stock.getPrice() == null || stock.getPrice() == 0.0) {
                System.out.println("Enter the price (in pounds) for " + stock.getStockSymbol() + ":");
                String input = scanner.nextLine();
                stock.setPrice(isDoubleInputValid(input));
            }
            showOutputForStock(stock);

            System.out.println("Do you wish to record a trade for " + stock.getStockSymbol() + "? (y/n)");
            String tradeInput = scanner.nextLine();
            if (tradeInput.toLowerCase().equals("y")) {
                createNewTrade(scanner);
                System.out.println("Volume Weighted Stock Price is: " + getVolumeWeightedStockPrice());
                System.out.println(EMPTY_STRING);
            }
        }
    }

    public void createNewTrade(Scanner scanner) {
        Integer shareQuantity = null;
        while (shareQuantity == null) {
            System.out.println("Enter the share quantity:");
            String shareQuantityInput = scanner.nextLine();
            shareQuantity = isIntegerInputValid(shareQuantityInput);
        }

        TradeType tradeTypeIndicator = null;
        while (tradeTypeIndicator == null) {
            System.out.println("'Buy' or 'Sell' trade:");
            String buySellInput = scanner.nextLine();
            tradeTypeIndicator = isTradeTypeInputValid(buySellInput);
        }

        Double tradedPrice = null;
        while (tradedPrice == null) {
            System.out.println("Enter the traded price:");
            String tradedPriceInput = scanner.nextLine();
            tradedPrice = isDoubleInputValid(tradedPriceInput);
        }

        trades.add(new Trade(shareQuantity, tradeTypeIndicator, tradedPrice));
    }

    public Double getVolumeWeightedStockPrice() {
        int sumOfPriceTimesQuantity = 0;
        int sumOfQuantities = 0;
        for (Trade trade : trades) {
            LocalDateTime fifteenMinesAgo = LocalDateTime.now().minusMinutes(15);
            if (trade.getTimestamp().isAfter(fifteenMinesAgo)) {
                sumOfPriceTimesQuantity += (trade.getTradedPrice() * trade.getShareQuantity());
                sumOfQuantities += trade.getShareQuantity();
            }
        }

        return (double) (sumOfPriceTimesQuantity / sumOfQuantities);

    }

    public Double isDoubleInputValid(String input) {
        try {
            Double parsedInput = Double.parseDouble(input);
            if (parsedInput == 0.0) {
                throw new NumberFormatException();
            }
            return parsedInput;
        } catch (NumberFormatException e) {
            System.out.println(ISSUE_WITH_NUMBER_TRY_AGAIN);
            return null;
        }
    }

    public Integer isIntegerInputValid(String input) {
        try {
            Integer parsedInput = Integer.parseInt(input);
            if (parsedInput == 0) {
                throw new NumberFormatException();
            }
            return parsedInput;
        } catch (NumberFormatException e) {
            System.out.println(ISSUE_WITH_NUMBER_TRY_AGAIN);
            return null;
        }
    }

    public TradeType isTradeTypeInputValid(String input) {
        if (input.toLowerCase().equals("buy")) {
            return TradeType.BUY;
        } else if (input.toLowerCase().equals("sell")) {
            return TradeType.SELL;
        } else {
            System.out.println("Issue with Buy/Sell input, try again");
            return null;
        }
    }

    public void showOutputForStock(Stock stock) {
        setFormulaValuesForStock(stock);
        System.out.println("Dividend Yield for " + stock.getStockSymbol() + ": " + stock.getDividendYield());
        System.out.println("P/E Ratio for " + stock.getStockSymbol() + ": " + stock.getPeRatio());
        System.out.println(EMPTY_STRING);
    }

    private void setFormulaValuesForStock(Stock stock) {
        StockType stockType = stock.getStockType();
        Double price = stock.getPrice();
        Double lastDividend = stock.getLastDividend();
        Double fixedDividend = stock.getFixedDividend();
        Double parValue = stock.getParValue();
        double dividendYield = 0.0;
        double peRatio = 0.0;

        if (stockType == StockType.COMMON && lastDividend != null && lastDividend != 0.0) {
            dividendYield = lastDividend / price;
            peRatio = price / dividendYield;
        } else if (stockType == StockType.PREFERRED && fixedDividend != null && fixedDividend != 0.0) {
            dividendYield = (fixedDividend * parValue) / price;
            peRatio = price / dividendYield;
        }
        stock.setDividendYield(round2(dividendYield));
        stock.setPeRatio(round2(peRatio));
    }

    public Double getGeometricMean() {
        return round2(stocks.size() * (Math.sqrt(stocks.stream().mapToDouble(a -> a.getPrice()).sum())));
    }

    public void setStocks(Stock... stocks) {
        this.stocks.clear();
        this.stocks.addAll(Arrays.asList(stocks));
    }

    private Double round2(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

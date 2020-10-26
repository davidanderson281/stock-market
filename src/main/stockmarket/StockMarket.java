package main.stockmarket;

import main.stockmarket.domain.Stock;
import main.stockmarket.domain.StockSymbol;
import main.stockmarket.domain.StockType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StockMarket {

    List<Stock> stocks = new ArrayList<>();

    public StockMarket() {
            createStocks();
            getUserInput();
            System.out.println("Geometric Mean for all stocks: " + getGeometricMean());
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
            boolean success = false;
            while (!success) {
                System.out.println("Enter the price (in pounds) for " + stock.getStockSymbol() + ":");
                String input = scanner.nextLine();
                success = isInputValid(input, stock);
            }
            showOutputForStock(stock);
        }
    }

    public boolean isInputValid(String input, Stock stock) {
        try {
            Double parsedInput = Double.parseDouble(input);
            if (parsedInput == 0.0) {
                throw new NumberFormatException();
            }
            stock.setPrice(parsedInput);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Issue with number, try again");
            return false;
        }
    }

    public void showOutputForStock(Stock stock) {
        setFormulaValuesForStock(stock);
        System.out.println("Dividend Yield for " + stock.getStockSymbol() + ": " + stock.getDividendYield());
        System.out.println("P/E Ratio for " + stock.getStockSymbol() + ": " + stock.getPeRatio());
        System.out.println("");
    }

    private void setFormulaValuesForStock(Stock stock) {
        StockType stockType = stock.getStockType();
        Double price = stock.getPrice();
        Double lastDividend = stock.getLastDividend();
        Double fixedDividend = stock.getFixedDividend();
        Double parValue = stock.getParValue();
        Double dividendYield = 0.0;
        Double peRatio = 0.0;

        if (stockType == StockType.COMMON && lastDividend != 0.0) {
            dividendYield = lastDividend / price;
            peRatio = price / dividendYield;
        } else if (stockType == StockType.PREFERRED && fixedDividend != 0.0) {
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

    public void clearStocks() {
        this.stocks.clear();
    }

    private Double round2(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

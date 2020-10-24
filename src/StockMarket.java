import domain.Stock;
import domain.StockSymbol;
import domain.StockType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StockMarket {

    List<Stock> stocks = new ArrayList<>();

    public StockMarket() {
        createStocks();
        getUserInput();
    }

    private void createStocks() {
        Stock stock1 = new Stock(StockSymbol.TEA, StockType.COMMON, 0.0, null, 1.00);
        Stock stock2 = new Stock(StockSymbol.POP, StockType.COMMON, 0.08, null, 1.00);
        Stock stock3 = new Stock(StockSymbol.ALE, StockType.COMMON, 0.23, null, 0.60);
        Stock stock4 = new Stock(StockSymbol.GIN, StockType.PREFERRED, 0.08, 0.02, 1.00);
        Stock stock5 = new Stock(StockSymbol.JOE, StockType.COMMON, 0.13, null, 2.50);
        stocks.addAll(Arrays.asList(stock1, stock2, stock3, stock4, stock5));
    }

    private void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        for (Stock stock : stocks) {
            boolean success = false;
            while (!success) {
                System.out.println("Enter the price for " + stock.getStockSymbol() + ":");
                String price = scanner.nextLine();
                try {
                    Double parsedInput = Double.parseDouble(price);
                    if (parsedInput == 0.0) {
                        throw new NumberFormatException();
                    }
                    stock.setPrice(parsedInput);
                    success = true;
                } catch (NumberFormatException e) {
                    System.out.println("Issue with number, try again");
                }
            }
            showOutputForStock(stock);
        }
    }
    private void showOutputForStock(Stock stock) {
        StockType stockType = stock.getStockType();
        Double price = stock.getPrice();
        Double lastDividend = stock.getLastDividend();
        Double fixedDividend = stock.getFixedDividend();
        Double parValue = stock.getParValue();

        if (stockType == StockType.COMMON) {
            if (lastDividend != 0.0) {
                stock.setDividendYield(lastDividend/price);
            }
        } else {
            if (fixedDividend != 0.0) {
                stock.setDividendYield((fixedDividend*parValue)/price);
            }
        }
        System.out.println("Dividend Yield for " + stock.getStockSymbol() + ": " + stock.getDividendYield());
    }
}

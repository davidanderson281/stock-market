package test.stockmarket;

import main.stockmarket.StockMarket;
import main.stockmarket.domain.Stock;
import main.stockmarket.domain.StockSymbol;
import main.stockmarket.domain.StockType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockMarketTest {


    @BeforeEach
    public void setUp() {
        String input = "1\n1\n1\n1\n1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    public void testFormulaValuesSetForCommonStock() {
        StockMarket testee = new StockMarket();
        Stock testStock = new Stock(StockSymbol.TEA, StockType.COMMON, 1.0, 1.0, 1.0);
        testStock.setPrice(2.0); // User input
        testee.showOutputForStock(testStock);
        assertEquals(0.5, testStock.getDividendYield());
        assertEquals(4, testStock.getPeRatio());
    }

    @Test
    public void testFormulaValuesSetForPreferredStock() {
        StockMarket testee = new StockMarket();
        Stock testStock = new Stock(StockSymbol.TEA, StockType.PREFERRED, 1.0, 1.0, 2.0);
        testStock.setPrice(2.0); // User input
        testee.showOutputForStock(testStock);
        assertEquals(1, testStock.getDividendYield());
        assertEquals(2, testStock.getPeRatio());
    }

    @Test
    public void testGenerateGeometricMeanWithSetupData() {
        StockMarket testee = new StockMarket();
        assertEquals(11.18, testee.getGeometricMean());
    }
}

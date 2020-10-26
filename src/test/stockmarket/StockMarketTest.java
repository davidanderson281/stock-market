package test.stockmarket;

import main.stockmarket.StockMarket;
import main.stockmarket.domain.Stock;
import main.stockmarket.domain.StockSymbol;
import main.stockmarket.domain.StockType;
import main.stockmarket.domain.TradeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StockMarketTest {


    @BeforeEach
    public void setUp() {
        String input = "1\nn\n1\nn\n1\nn\n1\nn\n1\nn";
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
    public void testFormulaValuesWhenInvalidStock() {
        StockMarket testee = new StockMarket();
        Stock testStock = new Stock(StockSymbol.TEA, StockType.COMMON, null, null, null);
        testStock.setPrice(2.0); // User input
        testee.showOutputForStock(testStock);
        assertEquals(0.0, testStock.getDividendYield());
        assertEquals(0.0, testStock.getPeRatio());
    }

    @Test
    public void testGenerateGeometricMeanWithSetupData() {
        StockMarket testee = new StockMarket();
        assertEquals(11.18, testee.getGeometricMean());
    }

    @Test
    public void testCreateATrade() {
        String input = "1\nn\n1\nn\n1\nn\n1\nn\n1\ny\n2\nBuy\n2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        StockMarket testee = new StockMarket();
        assertEquals(2.0, testee.getVolumeWeightedStockPrice());
    }

    @Test
    public void testDates() {
        StockMarket testee = new StockMarket();

        assertNull(testee.getVolumeWeightedStockPrice());
    }

    @Test
    public void testIsIntegerInputValid() {
        StockMarket testee = new StockMarket();
        assertNull(testee.isIntegerInputValid("0"));
    }

    @Test
    public void testIsDoubleInputValid() {
        StockMarket testee = new StockMarket();
        assertNull(testee.isDoubleInputValid("0.0"));
    }

    @Test
    public void testIsTradeTypeInputValid() {
        StockMarket testee = new StockMarket();
        assertEquals(TradeType.SELL, testee.isTradeTypeInputValid("Sell"));
        assertEquals(null, testee.isTradeTypeInputValid("Invalid"));
    }
}

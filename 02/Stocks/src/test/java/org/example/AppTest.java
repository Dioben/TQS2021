package org.example;


import Stocks.IStockMarket;
import Stocks.Stock;
import Stocks.StocksPortfolio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Unit test for simple App.
 */
@ExtendWith(MockitoExtension.class)
public class AppTest 
{
    @Test
    void getTotalValue(){
        //1.Prepare a mock to substitute the remote service (@Mock annotation)
        IStockMarket market = mock(IStockMarket.class);
        //2.Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
        StocksPortfolio portfolio = new StocksPortfolio();
        portfolio.setMarketService(market);
        // 3.Load the mock with the proper expectations (when...thenReturn)
        when(market.getPrice("APPLE")).thenReturn(4.0);
        when(market.getPrice("APPLE2")).thenReturn(1.5);
        portfolio.addStock(new Stock("APPLE",2));
        portfolio.addStock(new Stock("APPLE2",4));
        double result = portfolio.getTotalValue();
        // 4.Execute the test (use the service in the SuT)5.Verify the result (assert) and the use of the mock (verify)
        assertThat(result,is(14.0));
    }
}

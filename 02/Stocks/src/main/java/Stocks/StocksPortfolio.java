package Stocks;

public class StocksPortfolio {
    private String name;
    public IStockMarket getMarketService(){return  null;}
    public void setMarketService(IStockMarket x){;}
    public void addStock(Stock x){}
    public double getTotalValue(){return 0;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

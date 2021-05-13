package com.tbf;

/**
 * Stocks class inheriting Investments
 */
public class Stock extends Asset {
    public final double quarterlyDividend;
    public final double baseRateOfReturn;
    private final double betaMeasure;
    private final String stockSymbol;
    private final double sharePrice;
    double noOfShares;
    double stockReturn;

    public Stock(String code, String assetType, String label, double quarterlyDividend, double baseRateOfReturn, double betaMeasure, String stockSymbol, double sharePrice) {
        super(code, assetType, label);
        this.quarterlyDividend = quarterlyDividend;
        this.baseRateOfReturn = baseRateOfReturn;
        this.betaMeasure = betaMeasure;
        this.stockSymbol = stockSymbol;
        this.sharePrice = sharePrice;
    }

    public Stock(String code, String assetType, String label, double quarterlyDividend, double baseRateOfReturn, double betaMeasure, String stockSymbol, double sharePrice, double noOfShares) {
        super(code, assetType, label);
        this.quarterlyDividend = quarterlyDividend;
        this.baseRateOfReturn = baseRateOfReturn;
        this.betaMeasure = betaMeasure;
        this.stockSymbol = stockSymbol;
        this.sharePrice = sharePrice;
        this.noOfShares = noOfShares;
    }

    public Stock(Stock s){
        this(s.getCode(), s.getAssetType(), s.getLabel(), s.getQuarterlyDividend(), s.getBaseRateOfReturn(), s.getBetaMeasure(), s.getStockSymbol(), s.getSharePrice());
    }

    public double getQuarterlyDividend() {
        return quarterlyDividend;
    }
    public double getBaseRateOfReturn() {
        return baseRateOfReturn;
    }
    public double getBetaMeasure() {
        return betaMeasure;
    }
    public String getStockSymbol() {
        return stockSymbol;
    }
    public double getSharePrice() {
        return sharePrice;
    }
    public double getNoOfShares() {
        return noOfShares;
    }

    public double getValueAsset(){
        return sharePrice * noOfShares;
    }

    public double getAnnualReturn(){
        stockReturn = (baseRateOfReturn / 100 * sharePrice * noOfShares) + (4 * quarterlyDividend * noOfShares);
        return stockReturn;
    }

    public double getRiskMeasure(){
        return betaMeasure;
    }

    public Asset addData(double noOfShares){
        Stock stockCopy = new Stock(this);
        stockCopy.noOfShares = noOfShares;
        return stockCopy;
    }
}

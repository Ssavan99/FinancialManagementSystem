package com.tbf;

/**
 * Private Investment class inheriting Assets
 */
public class PrivateInvestment extends Asset {
    public final double quarterlyDividend;
    public final double baseRateOfReturn;
    private final double baseOmegaMeasure;
    private final double totalValue;
    double privateInvestmentReturn;
    double stakesOwned;

    public PrivateInvestment(String code, String assetType, String label, double quarterlyDividend, double baseRateOfReturn, double baseOmegaMeasure, double totalValue) {
        super(code, assetType, label);
        this.quarterlyDividend = quarterlyDividend;
        this.baseRateOfReturn = baseRateOfReturn;
        this.baseOmegaMeasure = baseOmegaMeasure;
        this.totalValue = totalValue;
    }

    public PrivateInvestment(String code, String assetType, String label, double quarterlyDividend, double baseRateOfReturn, double baseOmegaMeasure, double totalValue, double stakesOwned) {
        super(code, assetType, label);
        this.quarterlyDividend = quarterlyDividend;
        this.baseRateOfReturn = baseRateOfReturn;
        this.baseOmegaMeasure = baseOmegaMeasure;
        this.totalValue = totalValue;
        this.stakesOwned = stakesOwned;
    }

    public PrivateInvestment(PrivateInvestment p){
        this(p.getCode(), p.getAssetType(), p.getLabel(), p.getQuarterlyDividend(), p.getBaseRateOfReturn(), p.getBaseOmegaMeasure(), p.getTotalValue(), p.getStakesOwned());
    }

    public double getQuarterlyDividend() {
        return quarterlyDividend;
    }
    public double getBaseRateOfReturn() {
        return baseRateOfReturn;
    }
    public double getBaseOmegaMeasure() {
        return baseOmegaMeasure;
    }
    public double getTotalValue() {
        return totalValue;
    }
    public double getStakesOwned(){
        return stakesOwned;
    }

    public double getValueAsset(){
        double privateInvestmentValue = (stakesOwned/100) * totalValue;
        return privateInvestmentValue;
    }

    public double getAnnualReturn(){
        privateInvestmentReturn = stakesOwned / 100 * ((baseRateOfReturn/100 * totalValue) + (4 * quarterlyDividend));
        return privateInvestmentReturn;
    }

    public double getRiskMeasure(){
        return baseOmegaMeasure + Math.exp(-125500/totalValue);
    }

    @Override
    public Asset addData(double stakesOwned) {
        PrivateInvestment copyInvestment = new PrivateInvestment(this);
        copyInvestment.stakesOwned = stakesOwned;
        return copyInvestment;
    }
}

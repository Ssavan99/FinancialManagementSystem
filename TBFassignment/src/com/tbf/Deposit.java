package com.tbf;

import java.util.List;

/**
 * Deposit class inheriting Assets
 */
public class Deposit extends Asset {
    private final double apr;
    private double balance;
    double depositReturn;

    public Deposit(String code, String assetType, String label, double apr) {
        super(code, assetType, label);
        this.apr = apr;
    }

    public Deposit(String code, String assetType, String label, double apr, double balance){
        super(code, assetType, label);
        this.apr = apr;
        this.balance = balance;
    }

    public Deposit(Deposit d){
        this(d.getCode(), d.getAssetType(), d.getLabel(), d.getApr());
    }

    public double getApr() {
        return apr;
    }

    public double getValueAsset(){
        return balance;
    }

    public double getRiskMeasure(){
        return 0;
    }

    public Asset addData(double balance){
        Deposit depositCopy = new Deposit(this);

        depositCopy.balance = balance;
        return depositCopy;
    }

    public double getAnnualReturn(){
        double apy = Math.exp(apr/100) - 1;
        depositReturn = balance * apy;
        return depositReturn;
    }
}

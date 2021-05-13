package com.tbf;

import javax.sound.sampled.Port;
import java.util.Comparator;
import java.util.List;

/**
 * This class provides a general model of a portfolio
 */
public class Portfolio implements HaveCode{
    private String portfolioCode;
    private Person owner;
    private Broker manager;
    private Person beneficiary;
    private List<Asset> assetList;

    public Portfolio(String portfolioCode, Person owner, Broker manager, List<Asset> assetList) {
        this.portfolioCode = portfolioCode;
        this.owner = owner;
        this.manager = manager;
        this.assetList = assetList;
    }

    public Portfolio(String portfolioCode, Person ownerCode, Broker managerCode, Person beneficiaryCode, List<Asset> assetList){
        this(portfolioCode, ownerCode, managerCode, assetList);
        this.beneficiary = beneficiaryCode;
    }

    public String getCode() {
        return portfolioCode;
    }
    public Person getOwner() {
        return owner;
    }
    public Broker getManager() {
        return manager;
    }
    public Person getBeneficiary() {
        return beneficiary;
    }
    public List<Asset> getAssetList() {
        return assetList;
    }

    public double getTotalValue(){
        double totalValue = 0;
        for(Asset a : assetList){
            totalValue += a.getValueAsset();
        }
        return totalValue;
    }

    public double getTotalRisk(){
        double totalRisk = 0;
        for(Asset a : assetList){
            totalRisk += a.getRiskMeasure() * (a.getValueAsset() / getTotalValue());
        }
        return totalRisk;
    }

    public double getTotalReturn(){
        double totalReturn = 0;
        for (Asset a : assetList){
            totalReturn += a.getAnnualReturn();
        }
        return totalReturn;
    }

    public double getTotalFees(){
        int numAssets = assetList.size();
        return manager.getFees(numAssets);
    }

    public double getTotalCommission(){
        double expTotalReturn = getTotalReturn();
        return manager.getCommission(expTotalReturn);
    }


    final public static Comparator<Portfolio> CompareByOwner() {
        return new Comparator<Portfolio>() {
            public int compare(Portfolio p1, Portfolio p2) {
                if(p1.getOwner().getLastName().equals(p2.getOwner().getLastName())) {
                    return p1.getOwner().getFirstName().compareTo(p2.getOwner().getFirstName());
                } else {
                    return p1.getOwner().getLastName().compareTo(p2.getOwner().getLastName());
                }
            }
        };
    }

    final public static Comparator<Portfolio> CompareByValue() {
        return new Comparator<Portfolio>() {
            public int compare(Portfolio p1, Portfolio p2) {
                if(p1.getTotalValue() > p2.getTotalValue()) {
                    return -1;
                } else if(p1.getTotalValue() < p2.getTotalValue()){
                    return 1;
                } else {
                    return 0;
                }
            }
        };
    }

    final public static Comparator<Portfolio> CompareByManager() {
        return new Comparator<Portfolio>() {
            public int compare(Portfolio p1, Portfolio p2) {
                if (p1.getManager().getBrokerType().equals(p2.getManager().getBrokerType())) {
                    if (p1.getManager().getLastName().equals(p2.getManager().getLastName())) {

                        return p1.getOwner().getLastName().compareTo(p2.getManager().getLastName());
                    } else {
                        return p1.getManager().getLastName().compareTo(p2.getManager().getLastName());
                    }
                } else {
                    return p1.getManager().getBrokerType().compareTo(p2.getManager().getBrokerType());
                }
            }
        };
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioCode='" + portfolioCode + '\'' +
                ", owner=" + owner +
                ", manager=" + manager +
                ", beneficiary=" + beneficiary +
                ", assetList=" + assetList +
                '}';
    }
}

package com.tbf;

import java.util.*;

/**
 * This class generates a report for all the portfolios
 */
public class PortfolioFileReport {

    /**
     * This method generates a summary report of the portfolios
     * @param portfolios
     */
    private static void summaryReport(List<Portfolio> portfolios) {

        double combinedFees = 0;
        double combinedCommission = 0;
        double combinedReturn = 0;
        double combinedValue = 0;

        System.out.println("Portfolio Summary Report\n");
        System.out.println("===============================================================================================================================\n");
        System.out.printf("%-15s %-20s %-20s %10s %15s %15s %15s %15s\n", "Portfolio", "Owner", "Manager", "Fees", "Commissions", "Weighted Risk", "Return", "Total");

        for (Portfolio p : portfolios) {
            combinedFees += p.getTotalFees();
            combinedCommission += p.getTotalCommission();
            combinedReturn += p.getTotalReturn();
            combinedValue += p.getTotalValue();

            System.out.printf("%-15s %-20s %-20s $%9.2f $%14.2f %15.4f $%14.2f $%14.2f\n", p.getCode(), p.getOwner(), p.getManager(), p.getTotalFees(), p.getTotalCommission(), p.getTotalRisk(), p.getTotalReturn(), p.getTotalValue());
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%57s $%9.2f $%14.2f %15s $%14.2f $%14.2f", "Totals:", combinedFees, combinedCommission, "", combinedReturn, combinedValue);
    }

    /**
     * This method generates a detailed report of the portfolios
     * @param portfolios
     */
    private static void detailReport(List<Portfolio> portfolios) {

        System.out.println("\nPortfolio Details\n");
        System.out.println("================================================================================================================\n");
        for (Portfolio p : portfolios) {
            System.out.println("Portfolio " + p.getCode());
            System.out.println("-------------------");

            System.out.println("Owner:");
            System.out.println(p.getOwner());
            System.out.println(p.getOwner().getEmail());
            System.out.println(p.getOwner().getStreet() + "\n" + p.getOwner().getCity() + "," + p.getOwner().getState() + " " + p.getOwner().getCountry() + " " + p.getOwner().getZip());

            System.out.println("Manager:");
            System.out.println(p.getManager());

            System.out.println("Beneficiary:");
            if(p.getBeneficiary() != null) {
                System.out.println(p.getBeneficiary().getLastName() + "," + p.getBeneficiary().getFirstName());
                System.out.println(p.getBeneficiary().getEmail());
                System.out.println(p.getBeneficiary().getStreet() + "\n" + p.getBeneficiary().getCity() + "," + p.getBeneficiary().getState() + " " + p.getBeneficiary().getCountry() + " " + p.getBeneficiary().getZip());
            }
            else {
                System.out.println("None");
            }

            System.out.println("Assets");
            System.out.printf("%-30s %-40s %15s %10s %15s %15s\n", "Code", "Asset", "Return Rate", "Risk", "Annual Return", "Value");
            for(Asset a : p.getAssetList()) {
                System.out.printf("%-30s %-40s %14.2f%% %10.2f $%14.2f $%14.2f\n", a.getCode(), a.getLabel(),
                        a.getRateOfReturn(), a.getRiskMeasure(), a.getAnnualReturn(), a.getValueAsset());
            }

            System.out.printf("%110s\n", "------------------------------------");
            System.out.printf("%67s %10.2f $%14.2f $%14.2f\n", "Totals:", p.getTotalRisk(), p.getTotalReturn(), p.getTotalValue());
        }
    }

    public static void main(String[] args){
        List<Portfolio> portfolioList = FileLoad.portfolioFileLoad();
        /**
         * Sorts portfolio data according to the alphabetical order
         */
        portfolioList.sort(new Comparator<Portfolio>() {
            public int compare(Portfolio portfolioOne, Portfolio portfolioTwo) {
                String portfolioOneLastName = portfolioOne.getOwner().getLastName();
                String portfolioTwoLastName = portfolioTwo.getOwner().getLastName();
                return portfolioOneLastName.compareTo(portfolioTwoLastName);
            }
        });
        summaryReport(portfolioList);
        detailReport(portfolioList);

    }
}

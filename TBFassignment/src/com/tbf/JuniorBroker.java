package com.tbf;

import java.util.List;

/**
 * broker class inheriting persons class
 */
public class JuniorBroker extends Broker {
    private static String brokerType = "J";

    public JuniorBroker(String personCode, String juniorBroker, String secCode, String firstName, String lastName, String street, String city, String state, String zip, String country, List<String> email) {
        super(personCode, secCode, firstName, lastName, street, city, state, zip, country, email);
        //this.juniorBroker = juniorBroker;
    }

    public String getBrokerType() {
        return brokerType;
    }

    public double getFees(int numOfAsset){
        return 75.00 * numOfAsset;
    }

    @Override
    public double getCommission(double annualReturn) {
        return 0.0125 * annualReturn;
    }
}

package com.tbf;

import java.util.List;

public class ExpertBroker extends Broker {
    private final String brokerType = "E";

    public ExpertBroker(String personCode, String expertBroker, String secCode, String firstName, String lastName, String street, String city, String state, String zip, String country, List<String> email) {
        super(personCode, secCode, firstName, lastName, street, city, state, zip, country, email);
    }

    public String getBrokerType() {
        return brokerType;
    }

    public double getFees(int numOfAsset){
        return 0;
    }

    @Override
    public double getCommission(double annualReturn) {
        return 0.0375 * annualReturn;
    }
}

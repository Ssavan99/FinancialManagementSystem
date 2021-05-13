package com.tbf;

import java.util.List;

/**
 * This class models a broker, a specific type of person.
 */
public abstract class Broker extends Person {
    private final String secCode;
    /**
     * @param personCode
     * @param firstName
     * @param lastName
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param country
     * @param email
     */
    public Broker(String personCode, String secCode, String firstName, String lastName, String street, String city, String state, String zip, String country, List<String> email) {
        super(personCode, firstName, lastName, street, city, state, zip, country, email);
        this.secCode = secCode;
    }

    public String getSecCode() {
        return secCode;
    }


    public static String toStringBroker(Broker broker) {
        return "Broker{" +
                "secCode='" + broker.getSecCode() + '\'' +
                '}';
    }

    public abstract String getBrokerType();

    /**
     * Gets the annual fee charged by a broker on a portfolio
     * @param numOfAsset
     * @return
     */
    public abstract double getFees(int numOfAsset);

    /**
     * Gets the commission charged by a broker on a portfolio
     * @return
     */
    public abstract double getCommission(double annualReturn);
}

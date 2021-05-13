package com.tbf;

import java.util.List;

public class Customer extends Person {
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
    public Customer(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country, List<String> email) {
        super(personCode, firstName, lastName, street, city, state, zip, country, email);
    }
}
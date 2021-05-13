package com.tbf;

import java.util.List;

/**
 * class for persons
 */
public class Person implements HaveCode {
    private final String personCode;
    private final String firstName;
    private final String lastName;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String country;
    private final List<String> email;

    /**
     *
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
    public Person(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country, List<String> email){
        this.personCode = personCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.email = email;
    }

    //getters
    public String getCode() {
        return personCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public List<String> getEmail() {
        return email;
    }

    public String toString(){
        return lastName + "," + firstName;
    }

}

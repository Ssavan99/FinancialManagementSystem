package com.tbf;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseLoader {
    public static Logger log = Logger.getLogger("DatabaseLoader");

    public static int getPersonId(String personCode){
        Connection conn =  DatabaseInfo.getConnection();
        int personId = -1;
        String personCodeQuery = "SELECT personId FROM Person WHERE personCode = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(personCodeQuery);
            ps.setString(1, personCode);
            rs = ps.executeQuery();
            while(rs.next()) {
                personId = rs.getInt("personId");
            }
        } catch (Exception e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
        try {
            if(rs != null && !rs.isClosed())
                rs.close();
            if(ps != null && !ps.isClosed())
                ps.close();
            if(conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        return personId;

    }

    public static List<String> getAllEmailsPerson(String personCode){
        List<String> emails = new ArrayList<>();
        String email = null;

        Connection conn = DatabaseInfo.getConnection();
        Integer personId = getPersonId(personCode);
        String EmailQuery = "SELECT e.emailAddress FROM Person p JOIN Email e ON p.personId = e.personId WHERE p.personId = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(EmailQuery);
            ps.setInt(1, personId);
            rs = ps.executeQuery();
            while (rs.next()) {
                email = rs.getString("e.emailAddress");
                emails.add(email);
            }
        } catch (Exception e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
        try {
            if(rs != null && !rs.isClosed())
                rs.close();
            if(ps != null && !ps.isClosed())
                ps.close();
            if(conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return emails;
    }

    public static Person getPerson(String personCode){
        Person person = null;
        Connection conn = DatabaseInfo.getConnection();

        String PersonQuery = "SELECT p.personId, p.personCode, p.brokerType," +
                "p.firstName, p.lastName, a.street, a.city, a.state, a.country, a.zip " +
                "FROM Person p JOIN Address a ON p.addressId = a.addressId " +
                "WHERE p.personCode = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = conn.prepareStatement(PersonQuery);
            ps.setString(1, personCode);
            rs = ps.executeQuery();

            while(rs.next()) {
                String firstName = rs.getString("p.firstName");
                String lastName = rs.getString("p.lastName");
                String street = rs.getString("a.street");
                String city = rs.getString("a.city");
                String state = rs.getString("a.state");
                String country = rs.getString("a.country");
                String zip = rs.getString("a.zip");
                List<String> emails = getAllEmailsPerson(personCode);
                person = new Person(personCode, firstName, lastName, street, city, state, zip, country, emails);
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try{
            if (rs != null && !rs.isClosed()){
                rs.close();
            }
            if (ps != null && !ps.isClosed()){
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
        return person;
    }

    public static Broker getBroker(String personCode){
        Person person = getPerson(personCode);
        Broker broker = null;
        String brokerQuery = "SELECT p.brokerType, p.secId from Person p where personId = ?";
        int personId = getPersonId(personCode);

        Connection conn = DatabaseInfo.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = conn.prepareStatement(brokerQuery);
            ps.setInt(1, personId);
            rs = ps.executeQuery();
            while(rs.next()) {
                String brokerType = rs.getString("p.brokerType");
                String secId = rs.getString("p.secId");
                if(brokerType.equals("E")){
                    broker = new ExpertBroker(personCode, brokerType, secId, person.getFirstName(), person.getLastName(), person.getStreet(), person.getCity(), person.getState(), person.getZip(), person.getCountry(), person.getEmail());
                } else if(brokerType.equals("J")){
                    broker = new JuniorBroker(personCode, brokerType, secId, person.getFirstName(), person.getLastName(), person.getStreet(), person.getCity(), person.getState(), person.getZip(), person.getCountry(), person.getEmail());
                }
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try{
            if (rs != null && !rs.isClosed()){
                rs.close();
            }
            if (ps != null && !ps.isClosed()){
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
        return broker;
    }


    public static List<Person> getAllPersons(){
        List<Person> persons = new ArrayList<>();
        Person p;
        Connection conn = DatabaseInfo.getConnection();

        String PersonQuery = "SELECT p.personId, p.personCode, p.brokerType," +
                "p.firstName, p.lastName, a.street, a.city, a.state, a.country, a.zip " +
                "FROM Person p JOIN Address a ON p.addressId = a.addressId";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = conn.prepareStatement(PersonQuery);
            rs = ps.executeQuery();
            while (rs.next()) {
                String personCode = rs.getString("p.personCode");
                String brokerType = rs.getString("p.brokerType");
                String firstName = rs.getString("p.firstName");
                String lastName = rs.getString("p.lastName");
                String street = rs.getString("a.street");
                String city = rs.getString("a.city");
                String state = rs.getString("a.state");
                String country = rs.getString("a.country");
                String zip = rs.getString("a.zip");
                List<String> emails = getAllEmailsPerson(personCode);
                p = new Person(personCode, firstName, lastName, street, city, state, zip, country, emails);
                persons.add(p);
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try{
            if (rs != null && !rs.isClosed()){
                rs.close();
            }
            if (ps != null && !ps.isClosed()){
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
        System.out.println(persons);
        return persons;
    }


    public static int getAssetId(String assetCode){
        Connection conn =  DatabaseInfo.getConnection();
        int assetId;
        String assetCodeQuery = "SELECT assetId FROM Asset WHERE assetCode = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(assetCodeQuery);
            ps.setString(1, assetCode);
            rs = ps.executeQuery();
            rs.next();
            assetId = rs.getInt("assetId");
        } catch (Exception e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
        try {
            if(rs != null && !rs.isClosed())
                rs.close();
            if(ps != null && !ps.isClosed())
                ps.close();
            if(conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return assetId;
    }

    public static List<Asset> getAllAssets(String portfolioCode) {
        List<Asset> assetList = new ArrayList<>();
        Asset a = null;

        Connection conn = DatabaseInfo.getConnection();
        Integer personId = getPersonId(portfolioCode);
        String assetListQuery = "SELECT a.assetCode, a.assetType, a.label, a.apr, a.quarterlyDividend, " +
                "a.baseRateOfReturn, a.betaMeasure, a.stockSymbol, a.sharePrice, a.baseOmegaMeasure, a.totalValue, " +
                "pa.assetValue " +
                "FROM Portfolio p JOIN PortfolioAsset pa ON p.portfolioId = pa.portfolioId " +
                "JOIN Asset a ON pa.assetId = a.assetId WHERE p.portfolioCode = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(assetListQuery);
            ps.setString(1, portfolioCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                String assetCode = rs.getString("a.assetCode");
                String assetType = rs.getString("a.assetType");
                String label = rs.getString("a.label");
                if(assetType.equals("D")){
                    double apr = rs.getDouble("a.apr");
                    double totalBalance = rs.getDouble("pa.assetValue");
                    a = new Deposit(assetCode, assetType, label, apr, totalBalance);
                }
                if (assetType.equals("S")){
                    double quarterlyDividend = rs.getDouble("a.quarterlyDividend");
                    double baseRateOfReturn = rs.getDouble("a.baseRateOfReturn");
                    double betaMeasure = rs.getDouble("a.betaMeasure");
                    String stockSymbol = rs.getString("a.stockSymbol");
                    double sharePrice = rs.getDouble("a.sharePrice");
                    double numShares = rs.getDouble("pa.assetValue");
                    a = new Stock(assetCode, assetType, label, quarterlyDividend, baseRateOfReturn, betaMeasure, stockSymbol, sharePrice, numShares);
                }
                if (assetType.equals("P")){
                    double quarterlyDividend = rs.getDouble("a.quarterlyDividend");
                    double baseRateOfReturn = rs.getDouble("a.baseRateOfReturn");
                    double baseOmegaMeasure = rs.getDouble("a.baseOmegaMeasure");
                    double totalValue = rs.getDouble("a.totalValue");
                    double stacksOwned = rs.getDouble("pa.assetValue");
                    a = new PrivateInvestment(assetCode, assetType, label, quarterlyDividend, baseRateOfReturn, baseOmegaMeasure, totalValue, stacksOwned);
                }
                assetList.add(a);
            }
        } catch (Exception e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
        try {
            if(rs != null && !rs.isClosed())
                rs.close();
            if(ps != null && !ps.isClosed())
                ps.close();
            if(conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return assetList;
    }

    public static Portfolio getPortfolio(String portfolioCode){
        System.out.println("Inside portfolio");
        Portfolio p = null;
        Connection conn = DatabaseInfo.getConnection();

        String portfolioListQuery = "SELECT p.ownerCode, p.managerCode, p.beneficiaryCode FROM Portfolio p WHERE p.portfolioCode = ?";
        System.out.println("Query got executed");
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(portfolioListQuery);
            ps.setString(1, portfolioCode);
            rs = ps.executeQuery();
            while(rs.next()) {
               // String portfolioCode = rs.getString("p.portfolioCode");
                String ownerCode = rs.getString("p.ownerCode");
                String managerCode = rs.getString("p.managerCode");
                String beneficiaryCode = rs.getString("p.beneficiaryCode");
                p = new Portfolio(portfolioCode, getPerson(ownerCode), getBroker(managerCode), getPerson(beneficiaryCode), getAllAssets(portfolioCode));
            }
            System.out.println("Outside the while loop");
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try{
            if (rs != null && !rs.isClosed()){
                rs.close();
            }
            if (ps != null && !ps.isClosed()){
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
        System.out.println("Portfolio :::");
        System.out.println(p.toString());
        return p;
    }

    public static MyLinkedList<Portfolio> getAllPortfolios(Comparator comparator){

        MyLinkedList<Portfolio> portfolioList = new MyLinkedList<>(comparator);
        Portfolio p = null;
        Connection conn = DatabaseInfo.getConnection();

        String portfolioListQuery = "SELECT p.portfolioCode, p.ownerCode, p.managerCode, p.beneficiaryCode FROM Portfolio p ";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(portfolioListQuery);
            rs = ps.executeQuery();
            while(rs.next()) {

                String portfolioCode = rs.getString("p.portfolioCode");
                String ownerCode = rs.getString("p.ownerCode");
                String managerCode = rs.getString("p.managerCode");
                String beneficiaryCode = rs.getString("p.beneficiaryCode");
                p = new Portfolio(portfolioCode, getPerson(ownerCode), getBroker(managerCode), getPerson(beneficiaryCode), getAllAssets(portfolioCode));
                portfolioList.add(p);
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try{
            if (rs != null && !rs.isClosed()){
                rs.close();
            }
            if (ps != null && !ps.isClosed()){
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
        return portfolioList;
    }
}

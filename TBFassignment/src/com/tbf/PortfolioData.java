package com.tbf;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */

public class PortfolioData {
    public static Logger log = Logger.getLogger("PortfolioData");

    public static void removeAllPortfolioAsset() {
        Connection conn = DatabaseInfo.getConnection();

        List<Integer> assetIdList = new ArrayList<Integer>();

        String deleteAllPortfolioAsset = "SELECT assetId FROM PortfolioAsset";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(deleteAllPortfolioAsset);
            rs = ps.executeQuery();
            while (rs.next()){
                assetIdList.add((rs.getInt("assetId")));
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (rs != null && !ps.isClosed()) {
                rs.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        for (Integer assetId : assetIdList){
            removePortfolioAsset(assetId);
        }
    }

    public static void removePortfolioAsset(Integer assetId) {
        Connection conn = DatabaseInfo.getConnection();

        String deletePortfolioAsset = "DELETE FROM PortfolioAsset WHERE assetId = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deletePortfolioAsset);
            ps.setInt(1, assetId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
    }

    public static void removeAllPortfolioPerson() {
        Connection conn = DatabaseInfo.getConnection();

        List<Integer> personIdList = new ArrayList<Integer>();

        String deleteAllPortfolioPerson = "SELECT personId FROM PortfolioPerson";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(deleteAllPortfolioPerson);
            rs = ps.executeQuery();
            while (rs.next()){
                personIdList.add((rs.getInt("personId")));
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (rs != null && !ps.isClosed()) {
                rs.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        for (Integer personId : personIdList){
            removePortfolioPerson(personId);
        }
    }

    public static void removePortfolioPerson(Integer personId) {
        Connection conn = DatabaseInfo.getConnection();

        String deletePortfolioPerson = "DELETE FROM PortfolioPerson WHERE personId = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deletePortfolioPerson);
            ps.setInt(1, personId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that removes every person record from the database
     */
    public static void removeAllPersons() {
        removeAllPortfolios();
        removeAllEmail();
        Connection conn = DatabaseInfo.getConnection();
        List<String> personCodeList = new ArrayList<String>();

        String deleteAllPerson = "SELECT personCode FROM Person";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(deleteAllPerson);
            rs = ps.executeQuery();
            while (rs.next()){
                personCodeList.add((rs.getString("personCode")));
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (rs != null && !ps.isClosed()) {
                rs.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        for (String personCode : personCodeList){
            removePerson(personCode);
        }
    }

    /**
     * Removes the person record from the database corresponding to the
     * provided <code>personCode</code>
     * @param personCode
     */
    public static void removePerson(String personCode) {
        Connection conn = DatabaseInfo.getConnection();

        String deleteEmail = "DELETE FROM Email WHERE personId = (SELECT personId FROM Person WHERE personCode = ?)";
        PreparedStatement de = null;
        try {
            de = conn.prepareStatement(deleteEmail);
            de.setString(1, personCode);
            de.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        String deletePortfolioPerson = "DELETE FROM PortfolioPerson WHERE personId = (SELECT personId FROM Person WHERE personCode = ?)";
        PreparedStatement dpp = null;
        try {
            dpp = conn.prepareStatement(deletePortfolioPerson);
            dpp.setString(1, personCode);
            dpp.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        String deletePerson = "DELETE FROM Person WHERE personCode = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deletePerson);
            ps.setString(1, personCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (de != null && !de.isClosed()) {
                de.close();
            }
            if (dpp != null && !dpp.isClosed()) {
                dpp.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
    }

    public static void addAddress(String street, String city, String state, String zip, String country) {
        Connection conn = DatabaseInfo.getConnection();

        String addressQuery = "SELECT addressId FROM Address WHERE street = ? AND city = ? AND state = ? AND zip = ? AND country = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(addressQuery);
            ps.setString(1, street);
            ps.setString(2, city);
            ps.setString(3, state);
            ps.setString(4, zip);
            ps.setString(5, country);
            rs = ps.executeQuery();

            if(!rs.next()){
                rs.close();
                ps.close();

                String insertAddress = "INSERT INTO Address (street, city, state, zip, country) VALUES (?, ?, ?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertAddress);
                    ps.setString(1, street);
                    ps.setString(2, city);
                    ps.setString(3, state);
                    ps.setString(4, zip);
                    ps.setString(5, country);
                    //executing query
                    ps.executeUpdate();
                } catch (SQLException e) {
                    log.info("Exception Occurred");
                    throw new RuntimeException(e);
                }
            } else {
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }


        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to add a person record to the database with the provided data. The
     * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or
     * <code>null</code> if the person is not a broker.
     * @param personCode
     * @param firstName
     * @param lastName
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param country
     * @param brokerType
     */
    public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country, String brokerType, String secBrokerId) {
        addAddress(street, city, state, zip, country);
        Connection conn = DatabaseInfo.getConnection();

        String personInsert = "INSERT INTO Person (personCode, brokerType, secId, firstName, lastName, addressId) VALUES (?, ?, ?, ?, ?, (SELECT a.addressId FROM Address a WHERE a.street = ? AND a.city = ? AND a.state = ? AND a.zip = ? AND a.country = ?))";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(personInsert);
            ps.setString(1, personCode);
            ps.setString(2, brokerType);
            ps.setString(3, secBrokerId);
            ps.setString(4, firstName);
            ps.setString(5, lastName);
            ps.setString(6, street);
            ps.setString(7, city);
            ps.setString(8, state);
            ps.setString(9, zip);
            ps.setString(10, country);
            //executing query
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
    }

    public static void removeAllEmail() {
        Connection conn = DatabaseInfo.getConnection();

        List<Integer> emailList = new ArrayList<Integer>();

        String deleteAllEmail = "SELECT personId FROM Email";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(deleteAllEmail);
            rs = ps.executeQuery();
            while (rs.next()){
                emailList.add((rs.getInt("personId")));
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (rs != null && !ps.isClosed()) {
                rs.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        for (Integer personId : emailList){
            removeEmail(personId);
        }
    }

    public static void removeEmail(Integer personId) {
        Connection conn = DatabaseInfo.getConnection();

        String deleteEmail = "DELETE FROM Email WHERE personId = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deleteEmail);
            ps.setInt(1, personId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
            ps.close();
        }
            if (conn != null && !conn.isClosed()) {
            conn.close();
        }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds an email record corresponding person record corresponding to the
     * provided <code>personCode</code>
     * @param personCode
     * @param email
     */
    public static void addEmail(String personCode, String email) {
        Connection conn = DatabaseInfo.getConnection();

        String insertEmail = "INSERT INTO Email (personId, emailAddress) VALUES ((SELECT personId FROM Person WHERE personCode = ?), ?)";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(insertEmail);
            ps.setString(1, personCode);
            ps.setString(2, email);
            //executing query
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occured");
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes all asset records from the database
     */
    public static void removeAllAssets() {
        removeAllPortfolioAsset();
        Connection conn = DatabaseInfo.getConnection();

        List<String> assetCodeList = new ArrayList<String>();

        String deleteAllAsset = "SELECT assetCode FROM Asset";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(deleteAllAsset);
            rs = ps.executeQuery();
            while (rs.next()){
                assetCodeList.add((rs.getString("assetCode")));
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (rs != null && !ps.isClosed()) {
                rs.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        for (String assetCode : assetCodeList){
            removeAsset(assetCode);
        }
    }

    /**
     * Removes the asset record from the database corresponding to the
     * provided <code>assetCode</code>
     * @param assetCode
     */
    public static void removeAsset(String assetCode) {
        Connection conn = DatabaseInfo.getConnection();

        String deletePortfolioAsset = "DELETE FROM PortfolioAsset WHERE assetId = (SELECT assetId FROM Asset WHERE assetCode = ?)";
        PreparedStatement dpa = null;
        try {
            dpa = conn.prepareStatement(deletePortfolioAsset);
            dpa.setString(1, assetCode);
            dpa.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        String deleteAsset = "DELETE FROM Asset WHERE assetCode = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deleteAsset);
            ps.setString(1, assetCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (dpa != null && !dpa.isClosed()) {
                dpa.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occured");
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a deposit account asset record to the database with the
     * provided data.
     * @param assetCode
     * @param label
     * @param apr - Annual Percentage Rate on the scale [0, 1]
     */
    public static void addDepositAccount(String assetCode, String label, double apr) {
        Connection conn = DatabaseInfo.getConnection();

        PreparedStatement ps = null;

                String insertDeposit = "INSERT INTO Asset (assetCode, assetType, label, apr) VALUES (?, \"D\", ?, ?)";
                try{
                    ps = conn.prepareStatement(insertDeposit);
                    ps.setString(1, assetCode);
                    ps.setString(2, label);
                    ps.setDouble(3, apr);
                    //executing query
                    ps.executeUpdate();
                } catch (SQLException e) {
                    log.info("Exception Occurred");
                    throw new RuntimeException(e);
                }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occured");
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a private investment asset record to the database with the
     * provided data.
     * @param assetCode
     * @param label
     * @param quarterlyDividend
     * @param baseRateOfReturn on the scale [0, 1]
     * @param baseOmega
     * @param totalValue
     */
    public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend, Double baseRateOfReturn, Double baseOmega, Double totalValue) {
        Connection conn = DatabaseInfo.getConnection();

        PreparedStatement ps = null;

                String insertPrivateInvestment = "INSERT INTO Asset (assetCode, assetType, label, quarterlyDividend, baseRateOfReturn, baseOmegaMeasure, totalValue) VALUES (?, \"P\", ?, ?, ?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertPrivateInvestment);
                    ps.setString(1, assetCode);
                    ps.setString(2, label);
                    ps.setDouble(3, quarterlyDividend);
                    ps.setDouble(4, baseRateOfReturn);
                    ps.setDouble(5, baseOmega);
                    ps.setDouble(6, totalValue);
                    //executing query
                    ps.executeUpdate();
                } catch (SQLException e) {
                    log.info("Exception Occurred");
                    throw new RuntimeException(e);
                }

            try {

                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.info("Exception Occured");
                throw new RuntimeException(e);
            }
    }

    /** * Adds a stock asset record to the database with the
     * provided data.
     * @param assetCode
     * @param label
     * @param quarterlyDividend
     * @param baseRateOfReturn on the scale [0, 1]
     * @param beta
     * @param stockSymbol
     * @param sharePrice
     */
    public static void addStock(String assetCode, String label, Double quarterlyDividend, Double baseRateOfReturn, Double beta, String stockSymbol, Double sharePrice) {
        Connection conn = DatabaseInfo.getConnection();

        PreparedStatement ps = null;

                String insertStock = "INSERT INTO Asset (assetCode, assetType, label, quarterlyDividend, baseRateOfReturn, betaMeasure, stockSymbol, sharePrice) VALUES (?, \"S\", ?, ?, ?, ?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertStock);
                    ps.setString(1, assetCode);
                    ps.setString(2, label);
                    ps.setDouble(3, quarterlyDividend);
                    ps.setDouble(4, baseRateOfReturn);
                    ps.setDouble(5, beta);
                    ps.setString(6, stockSymbol);
                    ps.setDouble(7, sharePrice);
                    //executing query
                    ps.executeUpdate();
                } catch (SQLException e) {
                    log.info("Exception Occurred");
                    throw new RuntimeException(e);
                }


        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occured");
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes all portfolio records from the database
     */
    public static void removeAllPortfolios() {
        removeAllPortfolioAsset();
        removeAllPortfolioPerson();
        Connection conn = DatabaseInfo.getConnection();

        List<String> portfolioCodeList = new ArrayList<String>();

        String deleteAllPortfolio = "SELECT portfolioCode FROM Portfolio";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(deleteAllPortfolio);
            rs = ps.executeQuery();
            while (rs.next()){
                portfolioCodeList.add((rs.getString("portfolioCode")));
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (rs != null && !ps.isClosed()) {
                rs.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        for (String portfolioCode : portfolioCodeList){
            removePortfolio(portfolioCode);
        }
    }

    /**
     * Removes the portfolio record from the database corresponding to the
     * provided <code>portfolioCode</code>
     * @param portfolioCode
     */
    public static void removePortfolio(String portfolioCode) {

        Connection conn = DatabaseInfo.getConnection();
        String deletePortfolioPerson = "DELETE FROM PortfolioPerson WHERE portfolioId = (SELECT portfolioId FROM Portfolio WHERE portfolioCode = ?)";
        PreparedStatement dpp = null;
        try {
            dpp = conn.prepareStatement(deletePortfolioPerson);
            dpp.setString(1, portfolioCode);
            dpp.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        String deletePortfolioAsset = "DELETE FROM PortfolioAsset WHERE portfolioId = (SELECT portfolioId FROM Portfolio WHERE portfolioCode = ?)";
        PreparedStatement dpa = null;
        try {
            dpa = conn.prepareStatement(deletePortfolioAsset);
            dpa.setString(1, portfolioCode);
            dpa.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        String deletePortfolio = "DELETE FROM Portfolio WHERE portfolioCode = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deletePortfolio);
            ps.setString(1, portfolioCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (dpp != null && !dpp.isClosed()) {
                dpp.close();
            }
            if (dpa != null && !dpa.isClosed()) {
                dpa.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occured");
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a portfolio records to the database with the given data.  If the portfolio has no
     * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
     * @param portfolioCode
     * @param ownerCode
     * @param managerCode
     * @param beneficiaryCode
     */
    public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
        Connection conn = DatabaseInfo.getConnection();

        String insertPortfolio = "INSERT INTO Portfolio (portfolioCode, ownerCode, managerCode, beneficiaryCode) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insertPortfolio);
            ps.setString(1, portfolioCode);
            ps.setString(2, ownerCode);
            ps.setString(3, managerCode);
            ps.setString(4, beneficiaryCode);
            //executing query
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }

        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occured");
            throw new RuntimeException(e);
        }
    }

    /**
     * Associates the asset record corresponding to <code>assetCode</code> with
     * the portfolio corresponding to the provided <code>portfolioCode</code>.
     * The third parameter, <code>value</code> is interpreted as a <i>balance</i>,
     * <i>number of shares</i> or <i>stake percentage</i> (on the scale [0, 1])
     * depending on the type of asset the <code>assetCode</code> is associated
     * with.
     *
     * @param portfolioCode
     * @param assetCode
     * @param value
     */
    public static void addAsset(String portfolioCode, String assetCode, double value) {
        Connection conn = DatabaseInfo.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String portfolioAssetQuery = "SELECT pa.portfolioId FROM PortfolioAsset pa JOIN Portfolio p ON pa.portfolioId = p.portfolioId JOIN Asset a ON pa.assetId = a.assetId WHERE a.assetCode = ? AND p.portfolioCode = ?";
        try {
            ps = conn.prepareStatement(portfolioAssetQuery);
            ps.setString(1, assetCode);
            ps.setString(2, portfolioCode);
            rs = ps.executeQuery();

            if(!rs.next()) {
                ps.close();
                rs.close();

                String insertPortfolioAsset = "INSERT INTO PortfolioAsset (assetId, portfolioId, assetValue) VALUES ((SELECT assetId FROM Asset WHERE assetCode = ?), (SELECT portfolioId FROM Portfolio WHERE portfolioCode = ?), ?)";
                try {
                    ps = conn.prepareStatement(insertPortfolioAsset);
                    ps.setString(1, assetCode);
                    ps.setString(2, portfolioCode);
                    ps.setDouble(3, value);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    log.info("Exception Occurred");
                    throw new RuntimeException(e);
                }
            }else {
                ps.close();
                rs.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }



        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.info("Exception Occurred");
            throw new RuntimeException(e);
        }
    }
}

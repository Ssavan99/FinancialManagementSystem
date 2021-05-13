package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This is a collection of methods for loading and processing
 * data.
 */
public class FileLoad {

    private static <T extends HaveCode> Map<String, T> mapCodetoObject(List<T> objectList) {
        Map<String, T> map = new HashMap<String, T>();
        for(T item : objectList){
            map.put(item.getCode(), item);
        }
        return map;
    }

    /**
     * Loading and processing data of persons
     * @param fileName
     * @return
     */
    public static List<Person> fileLoad(String fileName) {
        List<Person> data = new ArrayList<>();
        Scanner s = null;
        try {
            //opening Persons.dat file to read and process data
            s = new Scanner(new File("data/Persons.dat"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        /**
         * Parsing line at ";" and ","
         */
        s.nextLine();
        while (s.hasNext()) {
            String line = s.nextLine();
            if(line.charAt(line.length() - 1) == ';') {
                line += " ";
            }
            if(!line.trim().isEmpty()){
                Person p = null;
                /**
                 * tokenizing line
                 */
                String[] tokens = line.split(";");
                String personCode = tokens[0];
                String[] brokerTokens = null;
                String brokerType = null;
                String secCode = null;
                if(!tokens[1].isEmpty()) {
                    brokerTokens = tokens[1].split(",");
                    brokerType = brokerTokens[0];
                    secCode = brokerTokens[1];
                }else{
                    String broker = tokens[1];
                }
                String[] nameTokens = tokens[2].split(",");
                String lastName = nameTokens[0];
                String firstName = nameTokens[1];
                String[] addressTokens = tokens[3].split(",");
                String street = addressTokens[0];
                String city = addressTokens[1];
                String state = addressTokens[2];
                String zip = addressTokens[3];
                String country = addressTokens[4];
                List<String> emails = new ArrayList<String>();
                if(tokens.length > 4) {
                    String[] emailTokens = tokens[4].split(",");
                    for(String email : emailTokens){
                        emails.add(email.trim());
                    }
                }

                /**
                 * populating class
                 */
                if(tokens[1].isEmpty()){
                    p = new Person(personCode, firstName, lastName, street, city, state, zip, country, emails);
                }else if(brokerTokens[0].equals("J")){
                    p = new JuniorBroker(personCode, brokerType, secCode, firstName, lastName, street, city, state, zip, country, emails);
                }else {
                    p = new ExpertBroker(personCode, brokerType, secCode, firstName, lastName, street, city, state, zip, country, emails);
                }
                data.add(p);
            }
        }
        s.close();
        return data;
    }

    /**
     * Loading and processing data of Assets
     * @param fileName
     * @return
     */
    public static List<Asset> loadFile(String fileName) {
        List<Asset> investments = new ArrayList<Asset>();
        Scanner t = null;

        try {
            t = new Scanner(new File("data/Assets.dat"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        t.nextLine();
        while (t.hasNext()){
            String lines = t.nextLine();
            if(lines.charAt(lines.length() - 1) == ';') {
                lines += " ";
            }
            if(!lines.trim().isEmpty()) {
                String[] tokens = lines.split(";");
                String code = tokens[0];
                String assetType = tokens[1];
                String label = tokens[2];
                double apr = 0.0, quarterlyDividend = 0.0, baseRateOfReturn = 0.0, betaMeasure = 0.0, sharePrice = 0.0, baseOmegaMeasure = 0.0, totalValue = 0.0;
                String stockSymbol = "";
                if (tokens.length == 4) {
                    apr = Double.parseDouble(tokens[3]);
                } else if (tokens.length == 8) {
                    quarterlyDividend = Double.parseDouble(tokens[3]);
                    baseRateOfReturn = Double.parseDouble(tokens[4]);
                    betaMeasure = Double.parseDouble(tokens[5]);
                    stockSymbol = tokens[6];
                    sharePrice = Double.parseDouble(tokens[7]);
                } else if (tokens.length == 7) {
                    quarterlyDividend = Double.parseDouble(tokens[3]);
                    baseRateOfReturn = Double.parseDouble(tokens[4]);
                    baseOmegaMeasure = Double.parseDouble(tokens[5]);
                    totalValue = Double.parseDouble(tokens[6]);
                }

                //populating class according to the asset type
                if (tokens[1].equals("D")) {
                   Asset a = new Deposit(code, assetType, label, apr);
                   investments.add(a);
                } else if (tokens[1].equals("S")) {
                    Asset a = new Stock(code, assetType, label, quarterlyDividend, baseRateOfReturn, betaMeasure, stockSymbol, sharePrice);
                    investments.add(a);
                }else if (tokens[1].equals("P")){
                    Asset a = new PrivateInvestment(code, assetType, label, quarterlyDividend, baseRateOfReturn, baseOmegaMeasure, totalValue);
                    investments.add(a);
                }
            }
        }
        t.close();
        return investments;
    }

    /**
     * Loading and processing data of portfolios
     * @return
     */
    public static List<Portfolio> portfolioFileLoad(){
        List<Portfolio> data = new ArrayList<Portfolio>();
        Map<String, Asset> assetMap = mapCodetoObject(loadFile("data/Assets.data"));
        Map<String, Person> personMap = mapCodetoObject(fileLoad("data/Assets.data"));
        Scanner s = null;
        try {
            //opening Portfolios.dat file to read and process data
            s = new Scanner(new File("data/Portfolios.dat"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        /**
         * Parsing line at ";" and ","
         */
        s.nextLine();
        while (s.hasNext()) {
            String line = s.nextLine();
            if(line.charAt(line.length() - 1) == ';') {
                line += " ";
            }
            if(!line.trim().isEmpty()) {
                String[] tokens = line.split(";");
                String portfolioCode = tokens[0];
                String ownerCode = tokens[1];
                String managerCode = tokens[2];
                String beneficiaryCode = "";
                List<Asset> assetsList = new ArrayList<Asset>();

                String[] assetsDetails = new String[0];
                try {
                    beneficiaryCode = tokens[3];
                    assetsDetails = tokens[4].split(",");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int assetsDetailsSize = assetsDetails.length;
                for(String a : assetsDetails){
                    String[] assetInfo = a.split(":");
                    String assetCode = assetInfo[0];
                    double numericValue = Double.parseDouble(assetInfo[1]);

                    Asset asset = assetMap.get(assetCode);
                    Asset portfolioAsset = asset.addData(numericValue);
                    assetsList.add(portfolioAsset);
                }

                Person owner = personMap.get(ownerCode);
                Person beneficiary = null;

                if(beneficiaryCode != "") {
                    beneficiary = personMap.get(beneficiaryCode);
                }
                Person possibleManager = personMap.get(managerCode);
                Broker manager = null;
                if(possibleManager instanceof Broker) {
                    manager = (Broker)personMap.get(managerCode);
                } else {
                    System.out.println("Invalid Portfolio The specified manager for portfolio ");
                    continue;
                }

                Portfolio pf = new Portfolio(portfolioCode, owner, manager, beneficiary, assetsList);
                data.add(pf);
            }
        }
        s.close();
        return data;
    }
}

package com.tbf;

import java.util.ArrayList;
import java.util.List;

public class DataConverter {

    public static void main(String[] args){
        List<Asset> investment = new ArrayList<>();
        investment = FileLoad.loadFile("data/Assets.dat"); //calling load file function
        xmlConverter.AssetXML(investment); //calling function to convert to xml
        List<Person> data1 = new ArrayList<>();
        data1 = FileLoad.fileLoad("data/Persons.dat");
        xmlConverter.PersonXML(data1);

        List<Asset> investments = new ArrayList<>();
        investments = FileLoad.loadFile("data/Assets.dat");
        JsonConverter.jsonAssets(investments);
        List<Person> data2 = new ArrayList<>();
        data2 = FileLoad.fileLoad("data/Persons.dat");
        JsonConverter.jsonPersons(data2);
    }
}
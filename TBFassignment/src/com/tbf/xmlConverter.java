package com.tbf;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class xmlConverter {
    public static void PersonXML (List<Person> persons){
        XStream xstream = new XStream();
        File personOutput = new File("data/Persons.xml");
        PrintWriter xmlPWriter = null;
        try {
            xmlPWriter = new PrintWriter(personOutput);
        }catch (FileNotFoundException e){
            e.getStackTrace();
        }

        xstream.alias("Persons", Person.class);

        xmlPWriter.write("<?xml version = \"1.0\" ?>\n");
        xmlPWriter.write("<persons>");
        /**
         * foreach loop for writing in xml form
         */
        for(Person person1 : persons){
            String output = xstream.toXML(person1);
            xmlPWriter.write(output + "\n"); //printing in the xml form in file
        }
        xmlPWriter.write("</persons>");
        xmlPWriter.close();
    }

    public static void AssetXML (List<Asset> asset){
        XStream xstream = new XStream();
        File assetOutput = new File("data/Assets.xml");
        PrintWriter xmlAWriter = null;
        try{
            xmlAWriter = new PrintWriter(assetOutput);
        }catch(FileNotFoundException e){
            e.getStackTrace();
        }

        xstream.alias("Assets", Asset.class);
        xmlAWriter.write("<?xml version = \"1.0\" ?>\n");
        xmlAWriter.write("<assets>");
        for(Asset asset1 : asset){
            String output = xstream.toXML(asset1);
            xmlAWriter.write(output + "\n");
        }
        xmlAWriter.write("</assets>");
        xmlAWriter.close();
    }
}

package com.tbf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class JsonConverter {
    public static void jsonPersons (List<Person> persons){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File person = new File("data/Persons.json");

        PrintWriter jsonPWriter = null;


        try {
            jsonPWriter = new PrintWriter(person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        jsonPWriter.write("{\n" + "\"Persons\": [");

            int i = 0;
            for(Person person1 : persons){
                String output = gson.toJson(person1);
                if (i++ == persons.size() - 1) {
                    jsonPWriter.write(output + "\n"); //printing in json form in file
                }else {
                    jsonPWriter.write(output + ",\n");
                }
        }
            jsonPWriter.write("]}");
        /**
         * closing writer
         */
        jsonPWriter.close();
    }

    public static void jsonAssets (List<Asset> asset){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File assets = new File("data/Assets.json");

        PrintWriter jsonAWriter = null;

        try{
            jsonAWriter = new PrintWriter(assets);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        jsonAWriter.write("{\n" + "\"assets\": [");

        int i = 0;
        /**
         * foreach loop for writing in json form
         */
        for(Asset asset1 : asset){
            String output = gson.toJson(asset1);
            if(i++ == asset.size() - 1) {
                jsonAWriter.write(output + "\n");
            }else{
                jsonAWriter.write(output + ",\n");
            }
        }
        jsonAWriter.write("]}");
        jsonAWriter.close();
    }
}

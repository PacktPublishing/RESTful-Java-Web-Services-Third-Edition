/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.packtpub.rest.ch2.model.Employee;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class demonstrates object model writer APIs in Gson
 *
 * @author Jobinesh
 */
public class GsonJsonObjectModelWriterExample {

    private static final Logger logger = Logger.getLogger(GsonJsonObjectModelWriterExample.class.getName());

    public static void main(String arg[]) throws IOException {
        logger.setLevel(Level.INFO);
        //Exercise the Gson model writer APIs
        String sourceFile = "emp-array.json";
        String destinationFile = "emp-array-modified.json";
        new GsonJsonObjectModelWriterExample().writeEmployeeList(sourceFile,destinationFile);

    }

    /**
     * This method reads JSON representation of employee object present in
     * sourceFile file and copies the contents in to destinationFile
     * file using model APIs
     *
     * @param sourceFile
     * @param destinationFile
     * @throws IOException
     */
    public void writeEmployeeList(String sourceFile,String destinationFile) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(destinationFile);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            
            List<Employee> employees = new GsonJsonObjModelReaderExample().buildEmployeeList("/"+sourceFile);
            
            Gson gson = new Gson();
            //Give Gson information on the specific generic type of List
            Type typeOfSource = new TypeToken<List<Employee>>() {
            }.getType();
            // create JSON String from Object
            String jsonEmps = gson.toJson(employees, typeOfSource);
            
            //You can easily  modify elements read from  emp-array.json
            //by converting them into List<Employee> as shown below
            //List<Employee> employees = gson.fromJson(reader, listType);
            logger.log(Level.INFO, jsonEmps);
            //Write the contents to emp-array-modified.json file
            bufferedWriter.write(jsonEmps);
            bufferedWriter.flush();
        }
    }

}

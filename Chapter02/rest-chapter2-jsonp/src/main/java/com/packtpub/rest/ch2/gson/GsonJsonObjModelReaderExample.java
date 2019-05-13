/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.gson;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.packtpub.rest.ch2.model.Employee;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.logging.Level;

/**
 * This class demonstrates object model reader APIs in Gson
 *
 * @author Jobinesh
 */
public class GsonJsonObjModelReaderExample {

    private static final Logger logger = Logger.getLogger(GsonJsonObjModelReaderExample.class.getName());

    public static void main(String arg[]) throws IOException {
        logger.setLevel(Level.INFO);
        //Exercise buildEmployeeList(String fileName) method
        List<Employee> employees = new GsonJsonObjModelReaderExample().buildEmployeeList("/emp-array.json");
        logger.log(Level.INFO, employees.toString());
    }

    /**
     * Reads JSON representation of employee array present in the input file and
     * converts the contents in to List of employee objects
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public List<Employee> buildEmployeeList(String fileName) throws IOException {
        // Override default date format and get Gson object
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        //Define the ArrayList<Employee> to store the JSON representation from input file
        Type listType = new TypeToken<ArrayList<Employee>>() {
        }.getType();
        InputStream inputStream = GsonJsonObjModelReaderExample.class.getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        //Converts JSON representation of employee objects in to List<Employee> 
        List<Employee> employees = gson.fromJson(reader, listType);
        //Following is just to exercise toJson method, not relevant otherwise
        //String jsonEmp = gson.toJson(employees);

        return employees;
    }
}

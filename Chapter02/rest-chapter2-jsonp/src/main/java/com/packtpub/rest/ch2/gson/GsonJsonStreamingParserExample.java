/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.gson;

import com.google.gson.stream.JsonReader;
import com.packtpub.rest.ch2.model.DateUtil;
import com.packtpub.rest.ch2.model.Employee;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class demonstrates streaming parser APIs in Gson
 *
 * @author Jobinesh
 */
public class GsonJsonStreamingParserExample {

    private static final Logger logger = Logger.getLogger(GsonJsonStreamingParserExample.class.getName());

    public static void main(String[] arg) throws FileNotFoundException, IOException {
        logger.setLevel(Level.INFO);
        String jsonFileName = "/emp-array.json";
        List<Employee> employeesList = new GsonJsonStreamingParserExample().buildEmployeeList(jsonFileName);
        logger.log(Level.INFO, employeesList.toString());
    }

    /**
     * This method converts JSON array of employee objects to a list of Employee
     * objects using Streaming APIs. The input file contains the JSON array.
     *
     * @param jsonFileName
     * @return
     * @throws IOException
     */
    public List<Employee> buildEmployeeList(String jsonFileName) throws IOException {
        List<Employee> employeeList = new ArrayList<>();

        //create JsonReader object
        //This class reads JSON encoded value as a stream of tokens. 
        //Tokens are read in the same order as they appear in the JSON document.
        try (InputStream inputStream = getClass().getResourceAsStream(jsonFileName);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                JsonReader reader = new JsonReader(inputStreamReader)) {
            reader.beginArray();
            while (reader.hasNext()) {
                Employee employee = readEmployee(reader);
                employeeList.add(employee);
            }
            reader.endArray();
        }
        return employeeList;
    }

    public Employee readEmployee(JsonReader reader) throws IOException {
        Employee employee = new Employee();
        /**
         * As this example uses array of JSON object’s, parser starts off by
         * calling beginArray(). This call consumes the array's opening bracket.
         * Client then call beginObject() to consume the object's opening brace.
         * This call is followed by a series of nextName() and next<DataType>
         * such nextString() to read the name-value representing the object.
         * After reading the entire object, client calls endObject() to consumes
         * the next token from the JSON stream and asserts that it is the end of
         * the current object. Once all objects are read, client then invokes
         * endArray() to consume the next token from the JSON stream and asserts
         * that it is the end of the current array.
         */
        reader.beginObject();
        while (reader.hasNext()) {
            String keyName = reader.nextName();
            switch (keyName) {
                case "firstName":
                    employee.setFirstName(reader.nextString());
                    break;
                case "lastName":
                    employee.setLastName(reader.nextString());
                    break;
                case "email":
                    employee.setEmail(reader.nextString());
                    break;
                case "employeeId":
                    employee.setEmployeeId(reader.nextInt());
                    break;
                case "hireDate":
                    Date date = DateUtil.getDate(reader.nextString());
                    //javax.xml.bind.DatatypeConverter.parseDateTime(reader.nextString());
                    employee.setHireDate(date);
                    break;
                default:
            }
        }
        reader.endObject();
        return employee;
    }

}

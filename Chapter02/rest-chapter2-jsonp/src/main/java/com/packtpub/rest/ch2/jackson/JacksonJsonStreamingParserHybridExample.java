/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packtpub.rest.ch2.model.Employee;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class demonstrates use of both Jackson binding and steaming parser APIs
 *
 * @author Jobinesh
 */
public class JacksonJsonStreamingParserHybridExample {

    private static final Logger logger = Logger.getLogger(JacksonJsonStreamingParserHybridExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);
        String jsonFileName = "/emp-array.json";
        JacksonJsonStreamingParserHybridExample jsonParserExample = new JacksonJsonStreamingParserHybridExample();
        List<Employee> employees = jsonParserExample.buildEmployeeList(jsonFileName);
        logger.log(Level.INFO, employees.toString());
    }

    /**
     * Reads JSON array of employees from input file using streaming APIs and
     * then builds List<Employee> using binding APIs
     *
     * @param jsonFileName
     * @return
     * @throws IOException
     */
    public List<Employee> buildEmployeeList(String jsonFileName) throws IOException {

        List<Employee> employeeList = new ArrayList<>();
       
        try (InputStream inpustStream = getClass().getResourceAsStream(jsonFileName);
                JsonParser jsonParser = new JsonFactory().createParser(inpustStream)) {

            ObjectMapper objectMapper = new ObjectMapper();
            //Continue the parsing till strem is opened or
            //when no more token is available
            while (!jsonParser.isClosed()) {
                JsonToken jsonToken = jsonParser.nextToken();
                // if its the last token then break the loop
                if (jsonToken == null) {
                    break;
                }
                //If this is start of the object, then create 
                //Employee instnace and add it to the result list
                if (jsonToken.equals(JsonToken.START_OBJECT)) {
                    //Use the objectMapper to bind the current object 
                    //to Employee object
                    Employee employee = objectMapper.readValue(jsonParser, Employee.class);
                    employeeList.add(employee);

                }

            }
        }
        return employeeList;
    }

}

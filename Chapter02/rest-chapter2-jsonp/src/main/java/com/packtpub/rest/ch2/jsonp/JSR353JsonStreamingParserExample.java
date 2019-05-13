/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.jsonp;

import com.packtpub.rest.ch2.model.DateUtil;
import com.packtpub.rest.ch2.model.Employee;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 * This class demonstrates the use of JSR353 streaming APIs for parsing JSON
 * file
 *
 * @author Jobinesh
 */
public class JSR353JsonStreamingParserExample {

    private static final Logger logger = Logger.getLogger(JSR353JsonStreamingParserExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);
        String jsonFileName = "/emp-array.json";
        JSR353JsonStreamingParserExample jsonParserExample = new JSR353JsonStreamingParserExample();
        List<Employee> employees = jsonParserExample.buildEmployeeList(jsonFileName);
        logger.log(Level.INFO, employees.toString());
    }

    /**
     * Return list of employees read from JSON file
     *
     * @param jsonFileName
     * @return
     * @throws IOException
     */
    public List<Employee> buildEmployeeList(String jsonFileName) throws IOException {

        List<Employee> employeeList = new ArrayList<>();
        Employee employee = null;

        //E.g To read remote  server over network is here:
        //String uri="http://date.jsontest.com/";
        //URL url = new URL();
        //InputStream inputStream = url.openStream();
        //JsonReader reader = Json.createReader(inputStream);
        try (InputStream inputStream = getClass().getResourceAsStream(jsonFileName);
                Reader reader = new InputStreamReader(inputStream, "UTF-8");
                JsonParser jsonParser = Json.createParser(reader)) {

            //Returns true if there are more parsing states
            while (jsonParser.hasNext()) {
                /**
                 * Client calls next() on JsonParser to advance the parser to
                 * the next state after processing each element. In response to
                 * the next() call, parser generate the following events based
                 * on the type of next token encountered: START_OBJECT,
                 * END_OBJECT, START_ARRAY, END_ARRAY, KEY_NAME, VALUE_STRING,
                 * VALUE_NUMBER, VALUE_TRUE, VALUE_FALSE, and VALUE_NULL.
                 */
                Event event = jsonParser.next();
                //Start of a JSON object, 
                //position of the parser is after'{'
                if (event.equals(JsonParser.Event.START_OBJECT)) {
                    employee = new Employee();
                    employeeList.add(employee);
                } else if (event.equals(JsonParser.Event.KEY_NAME)) {
                    String keyName = jsonParser.getString();
                    switch (keyName) {
                        case "firstName":
                            jsonParser.next();
                            employee.setFirstName(jsonParser.getString());
                            break;
                        case "lastName":
                            jsonParser.next();
                            employee.setLastName(jsonParser.getString());
                            break;
                        case "email":
                            jsonParser.next();
                            employee.setEmail(jsonParser.getString());
                            break;
                        case "employeeId":
                            jsonParser.next();
                            employee.setEmployeeId((jsonParser.getBigDecimal()).intValueExact());
                            break;
                        case "hireDate":
                            jsonParser.next();
                            employee.setHireDate(DateUtil.getDate(jsonParser.getString()));
                            break;
                        default:
                    }
                }

            }
        }

        return employeeList;
    }

}

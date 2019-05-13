/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.packtpub.rest.ch2.model.DateUtil;
import com.packtpub.rest.ch2.model.Employee;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class demonstrates steaming writer APIs in Jackson
 *
 * @author Jobinesh
 */
public class JacksonJsonStreamingParserExample {

    private static final Logger logger = Logger.getLogger(JacksonJsonStreamingParserExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);
        String jsonFileName = "/emp-array.json";
        JacksonJsonStreamingParserExample jsonParserExample = new JacksonJsonStreamingParserExample();
        List<Employee> employees = jsonParserExample.buildEmployeeList(jsonFileName);
        logger.log(Level.INFO, employees.toString());
    }

    /**
     * This method demonstrates the use of Jackson streaming reader API. It
     * reads the contents from input file and builds List<Employee>
     *
     * @param jsonFileName
     * @return
     * @throws IOException
     */
    public List<Employee> buildEmployeeList(String jsonFileName) throws IOException {

        List<Employee> employeeList = new ArrayList<>();
        Employee employee = null;

        try (InputStream inpustStream = getClass().getResourceAsStream(jsonFileName);
                JsonParser jsonParser = new JsonFactory().createParser(inpustStream)) {

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
                    employee = new Employee();
                    employeeList.add(employee);
                    //Pull the next element from parser
                    jsonParser.nextToken();
                }
                //Get the name associated with the current token
                String keyName = jsonParser.getCurrentName();
                //If the current token is pointint to array values 
                //or root-level values, getCurrentName() return null
                if (keyName == null) {
                    continue;
                }
                //Populate employee object manually 
                switch (keyName) {
                    case "firstName":
                        jsonParser.nextToken();
                        employee.setFirstName(jsonParser.getText());
                        break;
                    case "lastName":
                        jsonParser.nextToken();
                        employee.setLastName(jsonParser.getText());
                        break;
                    case "email":
                        jsonParser.nextToken();
                        employee.setEmail(jsonParser.getText());
                        break;
                    case "employeeId":
                        jsonParser.nextToken();
                        employee.setEmployeeId(jsonParser.getIntValue());
                        break;
                    case "hireDate":
                        jsonParser.nextToken();
                        employee.setHireDate(DateUtil.getDate(jsonParser.getText()));
                        break;
                    default:
                }
            }
        }
        return employeeList;
    }

}

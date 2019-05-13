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
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * This class demonstrates object model API for building Java model from JSON
 * string
 *
 * @author Jobinesh
 */
public class JSR353JsonObjModelReaderExample {

    private static final Logger logger = Logger.getLogger(JSR353JsonObjModelReaderExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);
        String jsonFileName = "/emp-array.json";
        JSR353JsonObjModelReaderExample jsonReaderExample = new JSR353JsonObjModelReaderExample();
        List<Employee> employees = jsonReaderExample.buildEmployeeList(jsonFileName);
        logger.log(Level.INFO, employees.toString());
    }

    /**
     * This method builds list of employees with the contents read from input
     * file. The input file contents are read using JSR353 object model reader
     * APIs
     *
     * @param jsonFileName
     * @return
     * @throws IOException
     */
    public List<Employee> buildEmployeeList(String jsonFileName) throws IOException {

        List<Employee> employeeList = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream(jsonFileName);
                Reader reader = new InputStreamReader(inputStream, "UTF-8");
                JsonReader jsonReader = Json.createReader(reader)) {

            /**
             * As the JSON data that we use in this example holds array of
             * employee objects, we call readArray() on JsonReader instance to
             * retrieve the javax.json.JsonArray. JsonArray instance contains an
             * ordered sequence of zero or more object read from the input
             * source
             */
            JsonArray employeeArray = jsonReader.readArray();
            for (JsonValue jsonValue : employeeArray) {
                if (jsonValue.getValueType().equals(JsonValue.ValueType.OBJECT)) {
                    JsonObject jsonObject = (JsonObject) jsonValue;
                    Employee employee = new Employee();
                    employee.setFirstName(jsonObject.getString("firstName"));
                    employee.setLastName(jsonObject.getString("lastName"));
                    employee.setEmail(jsonObject.getString("email"));
                    employee.setEmployeeId(jsonObject.getInt("employeeId"));
                    employee.setHireDate(DateUtil.getDate(jsonObject.getString("hireDate")));
                    employeeList.add(employee);
                }
            }

        }
        return employeeList;
    }
}

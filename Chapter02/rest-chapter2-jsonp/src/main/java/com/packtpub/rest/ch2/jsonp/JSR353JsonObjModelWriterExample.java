/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.jsonp;

import com.packtpub.rest.ch2.model.DateUtil;
import com.packtpub.rest.ch2.model.Employee;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

/**
 * This class demonstrates JR353 object model API for converting Object to JSON
 *
 * @author Jobinesh
 */
public class JSR353JsonObjModelWriterExample {

    private static final Logger logger = Logger.getLogger(JSR353JsonObjModelWriterExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);
        String outFile = "emp-array-modified.json";
        new JSR353JsonObjModelWriterExample().writeEmployeeList(outFile);
    }

    /**
     * Converts list of Employee object to JSON form and write to a file
     *
     * @throws IOException
     */
    public void writeEmployeeList(String outFile) throws IOException {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        List<Employee> employees = getEmployeeList();
        logger.log(Level.INFO, employees.toString());
        /**
         * Builds JsonArray from list of Employee
         */
        for (Employee employee : employees) {
            //Add desired name-value pairs to the array.
            //This example adds employee attributes and values 
            //that is read from Employee object

            jsonArrayBuilder.add(
                    Json.createObjectBuilder()
                            .add("employeeId", employee.getEmployeeId())
                            .add("firstName", employee.getFirstName())
                            .add("lastName", employee.getLastName())
                            .add("email", employee.getEmail())
                            .add("hireDate", DateUtil.getDate(employee.getHireDate()))
            );

        }
        JsonArray employeesArray = jsonArrayBuilder.build();

        try ( //write to file
                OutputStream outputStream = new FileOutputStream(outFile);
                Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
                JsonWriter jsonWriter = Json.createWriter(writer)) {

            jsonWriter.writeArray(employeesArray);
            writer.flush();
        }

    }

    /**
     * Create JsonObject object from Employee object using JsonObjectBuilder
     *
     * @param employee
     * @return
     */
    private JsonObject createJSONObject(Employee employee) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("employeeId", employee.getEmployeeId());
        objectBuilder.add("firstName", employee.getFirstName());
        objectBuilder.add("lastName", employee.getLastName());
        objectBuilder.add("email", employee.getEmail());
        objectBuilder.add("hireDate", employee.getHireDate().toString());
        JsonObject employeeJSONObj = objectBuilder.build();
        return employeeJSONObj;
    }

    /**
     * Gets the list of employees
     *
     * @return
     * @throws IOException
     */
    private List<Employee> getEmployeeList() throws IOException {
        String jsonFileName = "/emp-array.json";
        List<Employee> employeeList = new JSR353JsonObjModelReaderExample().buildEmployeeList(jsonFileName);
        return employeeList;
    }
}

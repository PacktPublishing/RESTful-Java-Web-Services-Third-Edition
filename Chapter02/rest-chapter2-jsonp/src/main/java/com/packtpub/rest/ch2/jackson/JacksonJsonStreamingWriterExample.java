/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.jackson;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.packtpub.rest.ch2.model.DateUtil;
import com.packtpub.rest.ch2.model.Employee;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class demonstrates steaming writer APIs in Jackson
 *
 * @author Jobinesh
 */
public class JacksonJsonStreamingWriterExample {

    private static final Logger logger = Logger.getLogger(JacksonJsonStreamingWriterExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);
        String outFie = "emp-array-modified.json";
        new JacksonJsonStreamingWriterExample().writeEmployeeList(outFie);
    }

    /**
     * This method demonstrates Jackson streaming writer APIs. It writes list of
     * employees in to output file
     *
     * @param outFie
     * @throws IOException
     */
    public void writeEmployeeList(String outFie) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        //JsonGenerator is used for writing JSON content to the output stream
        try ( OutputStream outputStream = new FileOutputStream(outFie); 
                //JsonGenerator is used for writing JSON content to the output stream
                JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8)) {
            /**
             * The method writeStartArray() writes starting marker for JSON array( [
             * ). Now write the marker for object ({) by calling writeStartObject().
             * This is followed by name-value pairs for the object by calling
             * appropriate write method. To write end marker for object ( } ), call
             * writeEndObject(). Finally to finish writing array, call
             * writeEndArray().
             */
            jsonGenerator.writeStartArray();
            List<Employee> employees = getEmployeesList();
            logger.log(Level.INFO, employees.toString());
            for (Employee employee : employees) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("employeeId", employee.getEmployeeId());
                jsonGenerator.writeStringField("firstName", employee.getFirstName());
                jsonGenerator.writeStringField("lastName", employee.getLastName());
                jsonGenerator.writeStringField("email", employee.getEmail());
                jsonGenerator.writeStringField("hireDate", DateUtil.getDate(employee.getHireDate()));
                jsonGenerator.writeEndObject();
                
            }
            jsonGenerator.writeEndArray();
            
        }
    }

    /**
     * Get the list of employees
     *
     * @return
     * @throws IOException
     */
    private List<Employee> getEmployeesList() throws IOException {
        String jsonFileName = "/emp-array.json";
        List<Employee> employeeList = new JacksonJsonStreamingParserExample().buildEmployeeList(jsonFileName);
        return employeeList;
    }
}

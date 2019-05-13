/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.jsonp;

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
import javax.json.stream.JsonGenerator;

/**
 * This class demonstrates streaming API for generating JSON representation for
 * Java object
 *
 * @author Jobinesh
 */
public class JSR353JsonStreamingGeneratorExample {

    private static final Logger logger = Logger.getLogger(JSR353JsonStreamingGeneratorExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);
        String outFile = "emp-array-modified.json";
        new JSR353JsonStreamingGeneratorExample().writeEmployeeList(outFile);
    }

    public void writeEmployeeList(String outFile) throws IOException {

        List<Employee> employees = getEmployeeList();
        logger.log(Level.INFO, employees.toString());
        try ( //write to file
                OutputStream outputStream = new FileOutputStream(outFile);
                Writer writer = new OutputStreamWriter(outputStream, "UTF-8"); 
                /**
                 * JsonGenerator writes JSON to an output source as specified by
                 * the client application. It generates name-value pairs for
                 * JSON objects and values for JSON arrays.
                 */
                JsonGenerator jsonGenerator = Json.createGenerator(writer)) {

            jsonGenerator.writeStartArray();
            for (Employee employee : employees) {
                jsonGenerator.writeStartObject()
                        .write("employeeId", employee.getEmployeeId())
                        .write("firstName", employee.getFirstName())
                        .write("lastName", employee.getLastName())
                        .write("email", employee.getEmail())
                        .write("hireDate", employee.getHireDate().toString())
                        .writeEnd();

            }
            jsonGenerator.writeEnd();
            writer.flush();
        }

    }

    /**
     * Gets list of employees for use in this example
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

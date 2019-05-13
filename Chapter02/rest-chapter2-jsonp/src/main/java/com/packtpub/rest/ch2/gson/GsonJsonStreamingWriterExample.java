/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.gson;

import com.google.gson.stream.JsonWriter;
import com.packtpub.rest.ch2.model.DateUtil;
import com.packtpub.rest.ch2.model.Employee;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class demonstrates Streaming writer APIs in Gson
 *
 * @author Jobinesh
 */
public class GsonJsonStreamingWriterExample {

    private static final Logger logger = Logger.getLogger(GsonJsonStreamingWriterExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);
        String sourceFile = "emp-array.json";
        String destinationFile = "emp-array-modified.json";
        new GsonJsonStreamingWriterExample().writeEmployeeList(sourceFile, destinationFile);
    }

    /**
     * This method reads JSON representation of objects from sourceFile and
     * copies the contents in to destinationFile using streaming APIs
     *
     * @param sourceFile
     * @param destinationFile
     * @throws IOException
     */
    public void writeEmployeeList(String sourceFile, String destinationFile) throws IOException {

        /**
         * This example writes JSON array representation of employee objects to
         * emp-array.json file. To write JSON content, we need to build
         * JsonWriter object which takes implementation of java.io.Writer as
         * input. Once the JsonWriter instance is created next is to start
         * writing the JSON content. As this example writes array of employee
         * objects, we start by invoking beginArray() method. This call begins
         * encoding a new array. Then call beginObject() to start encoding a new
         * object. This is followed by encoding of name and value. To finish
         * encoding of current object, call endObject() and to finish encoding
         * of current array, call endArray().
         */
        try (OutputStream outputStream = new FileOutputStream(destinationFile);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                JsonWriter writer = new JsonWriter(bufferedWriter)) {
            writer.beginArray();
            List<Employee> employees = new GsonJsonObjModelReaderExample().buildEmployeeList("/" + sourceFile);
            for (Employee employee : employees) {
                writeJSONObject(writer, employee);
            }
            writer.endArray();
            writer.flush();
        }
    }

    /**
     * This method writes a JSON (RFC 4627) encoded employee object to a stream
     *
     * @param writer
     * @param employee
     * @throws IOException
     */
    private void writeJSONObject(JsonWriter writer, Employee employee) throws IOException {
        /**
         * Then call beginObject() to start encoding a new employee object. This
         * is followed by encoding of name and value. To finish encoding of
         * current object, call endObject() and to finish encoding of current
         * array.
         */
        writer.beginObject();
        writer.name("employeeId").value(employee.getEmployeeId());
        writer.name("firstName").value(employee.getFirstName());
        writer.name("lastName").value(employee.getLastName());
        writer.name("email").value(employee.getEmail());
        writer.name("hireDate").value(DateUtil.getDate(employee.getHireDate()));
        writer.endObject();
    }
}

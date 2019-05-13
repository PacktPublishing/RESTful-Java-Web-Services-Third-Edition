/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.packtpub.rest.ch2.model.Employee;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class demonstrates object model(binding API) reader APIs in Jackson
 *
 * @author Jobinesh
 */
public class JacksonJsonObjModelReaderExample {

    private static final Logger logger = Logger.getLogger(JacksonJsonObjModelReaderExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);
        String jsonFileName = "/emp-array.json";
        List<Employee> emps = new JacksonJsonObjModelReaderExample().getEmployeeList(jsonFileName);
        logger.log(Level.INFO, "Employees full Jackson data binding are\n{0}", emps);
        String jsonString = "{\"employeeId\":100,\"firstName\":\"John\",\"lastName\":\"Chen\",\"email\":\"john.chen@xxxx.com\",\"hireDate\":\"2008-10-16\"}";

        Map properties = new JacksonJsonObjModelReaderExample().demoSimpleBinding(jsonString);
        logger.log(Level.INFO, "Employees simple binding is\n{0}{1}", new Object[]{properties, properties.size()});
    }

    /**
     * This example illustrates use of full Jackson data binding reader APIs for
     * converting JSON in to List<Employee>
     *
     * @param jsonFileName
     * @return
     * @throws IOException
     */
    public List<Employee> getEmployeeList(String jsonFileName) throws IOException {
        //read json file data to String
        InputStream inputStream = getClass().getResourceAsStream(jsonFileName);

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //convert json string to object
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class);
        List<Employee> emps = objectMapper.readValue(inputStream, collectionType);
        return emps;
    }

    /**
     * This method illustrates use of simple Jackson data binding reader APIs
     * for converting JSON string to Map
     *
     * @param jsonString
     * @return java.util.Map
     * @throws IOException
     */
    public Map<String, String> demoSimpleBinding(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //The following code snippet converts a jsonString to a Map object
        Map<String, String> properties = objectMapper.readValue(jsonString, new TypeReference<Map<String, String>>() {
        });
        return properties;

    }
}

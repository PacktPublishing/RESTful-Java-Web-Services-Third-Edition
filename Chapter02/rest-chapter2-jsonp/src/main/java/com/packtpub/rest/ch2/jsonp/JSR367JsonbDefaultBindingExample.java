/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.jsonp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import com.packtpub.rest.ch2.model.Employee;

/**
 * This class demonstrates default binding of JSON-B to map Java object to JSON
 * and vice-versa.
 *
 * @author Balachandar
 */
public class JSR367JsonbDefaultBindingExample {

    private static final Logger logger = Logger.getLogger(JSR367JsonbDefaultBindingExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);

        //Step-1:Read the Target JSON Document
        String jsonFileName = "/emp.json";
        InputStream inputStream = JSR374JsonPointerExample.class.getResourceAsStream(jsonFileName);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonObject = (JsonObject) jsonReader.read();
        
        
        //Step-2: Create instance of Jsonb using JsonbBuilder
        // Create custom configuration for parsing dates
        JsonbConfig dateConfig = new JsonbConfig().withDateFormat("yyyy-MM-dd",null);
        Jsonb jsonb = JsonbBuilder.create(dateConfig);
       
        //Step-3:Map JSON  to Employee Object
        Employee employee = jsonb.fromJson(jsonObject.toString(), Employee.class);

        logger.log(Level.INFO, "Employee Object constructed from JSON:{0}", employee);
        
        //Step-4:Create Jsonb instance with formatting enabled
        JsonbConfig config = new JsonbConfig().withFormatting(Boolean.TRUE);
        jsonb = JsonbBuilder.create(config);
        
        //Step-4:Map Employee Object to JSON
        String employeeJson = jsonb.toJson(employee);
        logger.log(Level.INFO, "JSON constructed from Employee object:{0}", employeeJson);
    }

}

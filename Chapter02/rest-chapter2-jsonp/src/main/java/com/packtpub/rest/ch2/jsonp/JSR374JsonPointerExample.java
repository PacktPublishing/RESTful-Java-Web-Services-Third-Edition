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
import javax.json.JsonPointer;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * This class demonstrates usage of JSON Pointer
 *
 * @author Balachandar
 */
public class JSR374JsonPointerExample {

    private static final Logger logger = Logger.getLogger(JSR374JsonPointerExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);

        //Read the Target JSON Document
        String jsonFileName = "/emp.json";
        InputStream inputStream = JSR374JsonPointerExample.class.getResourceAsStream(jsonFileName);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
    
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonObject = (JsonObject) jsonReader.read();

        //Create the Pointer passing the reference location
        JsonPointer pointer = Json.createPointer("/firstName");

        //Fetch the firstName from target object using the pointer
        JsonValue value = pointer.getValue(jsonObject);

        logger.log(Level.INFO, "Fetched First Name:{0}", value.toString());

        //Alter the first name to different value
        JsonValue replaceFirstName = Json.createValue("Mike");
        jsonObject = pointer.add(jsonObject, replaceFirstName);

        //Fetch the modified firstName from target object using the pointer
        value = pointer.getValue(jsonObject);

        logger.log(Level.INFO, "Modified First Name:{0}", value.toString());
    }
    
    
}

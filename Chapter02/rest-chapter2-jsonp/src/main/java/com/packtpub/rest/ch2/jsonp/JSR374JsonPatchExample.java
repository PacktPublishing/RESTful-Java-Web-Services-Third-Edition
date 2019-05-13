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
import javax.json.JsonPatch;
import javax.json.JsonPatchBuilder;
import javax.json.JsonReader;

/**
 * This class demonstrates usage of JSON Patch
 *
 * @author Balachandar
 */
public class JSR374JsonPatchExample {

    private static final Logger logger = Logger.getLogger(JSR374JsonPointerExample.class.getName());

    public static void main(String[] args) throws IOException {
        logger.setLevel(Level.INFO);

        //Read the Target JSON Document
        String jsonFileName = "/emp.json";
        InputStream inputStream = JSR374JsonPointerExample.class.getResourceAsStream(jsonFileName);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
    
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonObject = (JsonObject) jsonReader.read();

        logger.log(Level.INFO, "Input Employee:{0}", jsonObject.toString());
        
        //Instantiate the Patch Builder
        JsonPatchBuilder patchBuilder = Json.createPatchBuilder();
        
        //Add gender data to Employee data
        patchBuilder.add("/gender", "Male");
        //Remove the hireDate from Employee data
        patchBuilder.remove("/hireDate");
        
        //Construct and apply the patch operations on the
        //target object
        JsonPatch patch = patchBuilder.build();
        jsonObject = patch.apply(jsonObject);


        logger.log(Level.INFO, "Output Employee:{0}", jsonObject.toString());
    }

}

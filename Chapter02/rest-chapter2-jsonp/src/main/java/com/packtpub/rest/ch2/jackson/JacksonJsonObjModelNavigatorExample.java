/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package com.packtpub.rest.ch2.jackson;

import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class demonstrates object model navigator APIs in Jackson
 *
 * @author Jobinesh
 */
public class JacksonJsonObjModelNavigatorExample {

    private static final Logger logger = Logger.getLogger(JacksonJsonObjModelNavigatorExample.class.getName());

    public static void main(String[] arg) throws IOException {
        logger.setLevel(Level.INFO);
        String jsonFileName = "/emp-array.json";
        new JacksonJsonObjModelNavigatorExample().findAndUpdateNode(jsonFileName);
    }

    /**
     * This method reads the JSON object array representation of employees from
     * input file and then navigates the employee objects to find objects that
     * are not having email attribute. This example update those object with
     * dummy email and writes the modified contents in to
     * emp-array-modified.json file
     *
     * @param jsonFileName
     * @throws IOException
     */
    public void findAndUpdateNode(String jsonFileName) throws IOException {
        //read json file 
        InputStream inputStream = getClass().getResourceAsStream(jsonFileName);
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //read JSON like DOM Parser
        JsonNode rootNode = objectMapper.readTree(inputStream);
        if (rootNode.isArray()) {
            for (JsonNode objNode : rootNode) {
                System.out.println(objNode);
                JsonNode idNode = objNode.path("employeeId");
                logger.log(Level.INFO, "id = {0}", idNode.asInt());

                JsonNode emailNode = objNode.path("email");
                logger.log(Level.INFO, "email = {0}", emailNode.textValue());
                if (emailNode.textValue() == null) {
                    ((ObjectNode) objNode).put("email", "unknown");
                }
                objectMapper.writeValue(new File("emp-array-modified.json"), rootNode);
            }
        }

    }

}

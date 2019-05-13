/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.client;

import com.packtpub.rest.ch6.filter.ClientAuthenticationFilter;
import com.packtpub.rest.ch6.model.Department;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * JAX-RS REST client generated for REST resource:HRService [hr]<br>
 *
 *
 * @author Balachandar
 */
public class HRServiceBasicAuthClient {

    private WebTarget webTarget;
    private Client client;

    private static final String BASE_URI = "http://localhost:15647/rest-chapter6-service/webresources";
    private static final Logger logger = Logger.getLogger(HRServiceBasicAuthClient.class.getName());

    public HRServiceBasicAuthClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("hr").path("departments");
        //Register the filter with client
        ClientAuthenticationFilter filter = new ClientAuthenticationFilter("hr", "hr");
        webTarget.register(filter);
    }

    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {

        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void disconnect() {
        client.close();
    }

    public static void main(String arg[]) {

        HRServiceBasicAuthClient hrServiceClient = new HRServiceBasicAuthClient();
        List<Department> depts = hrServiceClient.findAll(new GenericType<List<Department>>() {
        });

        logger.log(Level.INFO, depts.toString());

        hrServiceClient.disconnect();
    }

}

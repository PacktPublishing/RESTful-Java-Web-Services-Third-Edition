/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.client;

import com.packtpub.rest.ch6.model.Department;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * JAX-RS REST client generated for REST resource:HRService [hr]<br>
 * to demonstrate JWT Authentication
 *
 * @author Balachandar
 */
public class HRServiceJWTAuthClient {

    private WebTarget webTarget;
    private final Client client;

    private static final String BASE_URI = "http://localhost:15647/rest-chapter6-jwtservice/webresources";
    private static final Logger logger = Logger.getLogger(HRServiceJWTAuthClient.class.getName());

    /**
    * Default Constructor
    */    
    public HRServiceJWTAuthClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
    }

    /**
    * Retrieves the JWT token for the supplied login credentials
    * 
    * @return JWT Token
    */     
    private String getToken(String loginId, String loginKey) {
        String jwtToken = "";

        webTarget = client.target(BASE_URI).path("hr").path("login");

        Form form = new Form();
        form.param("login", loginId);
        form.param("password", loginKey);

        Response response = webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if (response.getStatus() == 200) {
            jwtToken = response.getHeaderString(HttpHeaders.AUTHORIZATION);

        }

        return jwtToken;
    }

    /**
    * Find the department for the supplied department id using valid JWT token
    * 
    * @return Department
    */       
    public <T> T findDepartMent(Class<T> responseType, String id, String token) 
            throws ClientErrorException {
        webTarget = client.target(BASE_URI).path("hr").
                        path("departments/{id}").resolveTemplate("id", id);

        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).
                    header(HttpHeaders.AUTHORIZATION, token).get(responseType);
    }

    /**
    * Close the client connection
    */     
    private void disconnect() {
        client.close();
    }

    public static void main(String arg[]) {

        HRServiceJWTAuthClient hrServiceClient = new HRServiceJWTAuthClient();

        //Step-1: Submit the login credential to fetch the JWT token
        String token = hrServiceClient.getToken("xxxxxx", "yyyyyy");

        logger.log(Level.INFO, "Obtained Token:{0}", token);

        //Step-2: Find the department using the valid token
        logger.log(Level.INFO, "Outcome using valid token:{0}", token);
        if (token != null && !"".equals(token.trim())) {
            Department dept = hrServiceClient.findDepartMent(Department.class, "10", token);
            if(dept != null)
                logger.log(Level.INFO, dept.toString());
        }

        //Step-3: Find the department using invalid token
        try
        {
            token = "t@´—à%O˜v+nî…SYg¯µ$%…9cZ";
            logger.log(Level.INFO, "Outcome using invalid token:{0}", token);
            Department dept = hrServiceClient.findDepartMent(Department.class, "10", token);
            if(dept != null)
             logger.log(Level.INFO, dept.toString());
        }catch(Exception e)
        {
            logger.log(Level.SEVERE,e.getMessage());
        }
                
        hrServiceClient.disconnect();
    }

}

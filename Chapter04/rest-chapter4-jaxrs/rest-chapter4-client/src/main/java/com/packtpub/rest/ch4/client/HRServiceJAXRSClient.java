/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.client;

import com.packtpub.rest.ch4.model.Department;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * JAX-RS REST client generated for REST resource:HRService [hr]<br>
 *
 *
 * @author Jobinesh
 */
public class HRServiceJAXRSClient {

    private static final Logger logger = Logger.getLogger(HRServiceJAXRSClient.class.getName());
    private static final String BASE_URI = "http://localhost:12255/rest-chapter4-service/webresources";

    public HRServiceJAXRSClient() {

    }

    public void create() throws ClientErrorException {
        Client client = javax.ws.rs.client.ClientBuilder.newClient();
        WebTarget webTarget = client.target(BASE_URI).path("hr");
        Form form = new Form();
        form.param("departmentId", "1003");
        form.param("departmentName", "RevenueXXX");
        
        
        Department dept = new Department();
        dept.setDepartmentId(Short.valueOf("1003"));
        dept.setDepartmentName("XXX");
        dept.setLocationId(Short.valueOf("2500"));
        dept.setManagerId(108);
        
         //final Response response =webTarget.path("departments/form").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(form, javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED));
        final Response response =webTarget.path("departments").request(MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.json(dept));
        logger.log(Level.INFO, "{0}-{1}", new Object[]{response.getStatusInfo().toString(), response.getStatus()});
        
        client.close();
    }

    public static void main(String arg[]) {
        
        HRServiceJAXRSClient client = new HRServiceJAXRSClient();
        client.create();
        
    }

}

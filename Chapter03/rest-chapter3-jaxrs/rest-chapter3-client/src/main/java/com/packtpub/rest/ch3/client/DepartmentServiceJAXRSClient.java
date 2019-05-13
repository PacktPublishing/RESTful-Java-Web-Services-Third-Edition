/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch3.client;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import com.packtpub.rest.ch3.model.Departments;

/**
 * JAX-RS client for accessing /departments resource
 *
 * @author Jobinesh
 */
public class DepartmentServiceJAXRSClient {

    private WebTarget webTarget;
    private Client client;
   
    private static final String BASE_URI = "http://localhost:12255/rest-chapter3-service/webresources";
    private static final Logger logger = Logger.getLogger(DepartmentServiceJAXRSClient.class.getName());

    public DepartmentServiceJAXRSClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("departments");
    }

    public DepartmentServiceJAXRSClient(String username, String password) {
        this();
        setUsernamePassword(username, password);
    }

    public static void main(String[] args) {
        // Reads all department resource
        List<Departments> depts = new DepartmentServiceJAXRSClient().findAll(new GenericType<List<Departments>>() {
        });
        logger.log(Level.INFO, depts.toString());
        //Finds a specific department resource
        Departments dept = new DepartmentServiceJAXRSClient().find(Departments.class, "10");
        logger.log(Level.INFO, dept.toString());
        //Posts modified department to REST API 
        dept.setDepartmentName("Software Development");
        new DepartmentServiceJAXRSClient().edit(dept, "10");
        logger.log(Level.INFO, "Dept modified");
    }

    public Integer countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        Builder builder = resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN);
        Invocation invoation = builder.buildGet();
        return invoation.invoke(Integer.class);
    }

    public void edit(Object requestEntity, String id) throws ClientErrorException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}",
                new Object[]{id}));
        Builder builder = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        builder.put(javax.ws.rs.client.Entity.entity(requestEntity,
                javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void create() throws ClientErrorException {
        webTarget.request().post(null);
    }

    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public void close() {
        client.close();
    }

    public final void setUsernamePassword(String username, String password) {
        //  webTarget.register(new org.glassfish.jersey.client.filter.HttpBasicAuthFilter(username, password));
    }
}

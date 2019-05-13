/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.resteasy.client;

import com.packtpub.rest.ch5.resteasy.model.Departments;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.cache.BrowserCacheFeature;
import org.jboss.resteasy.client.jaxrs.cache.LightweightBrowserCache;

/**
 * JAX-RS client for accessing /departments resource using RESTEasy client side
 * caching.
 *
 * @author Balachandar
 */
public class DepartmentServiceClient {

    private static final Logger logger = Logger.getLogger(DepartmentServiceClient.class.getName());

    private final ResteasyWebTarget webTarget;
    private static final String BASE_URI = "http://localhost:15647/rest-chapter5-resteasy/webresources/departments";

    /**
     * Default Constructor
     *
     */
    public DepartmentServiceClient() {
        webTarget = (ResteasyWebTarget) ClientBuilder.newClient().target(BASE_URI);

        //Step1: Construct an instance of RESTEasy LightweightBrowserCache
        LightweightBrowserCache cache = new LightweightBrowserCache();
        //Step2: Default 2 MB of data is the max size, override as relevant
        cache.setMaxBytes(20);
        //Step3: Apply the BrowserCacheFeature to the target resource to enable caching
        BrowserCacheFeature cacheFeature = new BrowserCacheFeature();
        cacheFeature.setCache(cache);

        webTarget.register(cacheFeature);
    }

    /**
     * Finds a department by id
     *
     * @param id
     * @return Departments
     */
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {

        return webTarget.path("id/{id}").resolveTemplate("id", "10").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Find all departments
     *
     * @return List of Departments
     */
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {

        return webTarget.path("all").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public static void main(String[] args) {

        DepartmentServiceClient client = new DepartmentServiceClient();

        //Finds a specific department resource
        Departments dept = client.find(Departments.class, "10");
        logger.log(Level.INFO, "Caching Enabled:{0}", client.webTarget.getConfiguration().getProperties().toString());
        logger.log(Level.INFO, dept.toString());

        List<Departments> depts = client.findAll(new GenericType<List<Departments>>() {
        });

        logger.log(Level.INFO, depts.toString());
    }
}

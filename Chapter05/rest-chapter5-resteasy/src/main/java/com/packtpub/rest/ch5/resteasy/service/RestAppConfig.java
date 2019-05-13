/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.resteasy.service;

/**
 * This is the configuration class for this JAX-RS application
 *
 * @author Balachandar
 */
import java.util.Set;
import javax.ws.rs.core.Application;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataReader;


@javax.ws.rs.ApplicationPath("webresources")

public class RestAppConfig extends Application {


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(com.packtpub.rest.ch5.resteasy.service.DepartmentService.class);
        resources.add(com.packtpub.rest.ch5.resteasy.service.EmployeeImageResource.class);
        resources.add(MultipartFormDataReader.class);

        return resources;
    }

}

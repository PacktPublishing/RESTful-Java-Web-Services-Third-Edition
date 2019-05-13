/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch3.service;

/**
 * This is the configuration class for this JAX-RS application
 *
 * @author Jobinesh
 */
import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("webresources")
public class RestAppConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(com.packtpub.rest.ch3.service.DepartmentService.class);
        return resources;
    }
}

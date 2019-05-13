/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch8.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.message.filtering.SelectableEntityFilteringFeature;

/**
 * Defines the components of a JAX-RS application and supplies additional
 * metadata.
 *
 * @author Jobinesh
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        //Disabled as SelectableEntityFilteringFeature thows error for JSON- Jersey bug
        resources.add(SelectableEntityFilteringFeature.class);

        return resources;
    }

    @Override
    public Map<String, Object> getProperties() {
        Map propeerites = new HashMap();
        propeerites.put(SelectableEntityFilteringFeature.QUERY_PARAM_NAME, "select");
        return propeerites; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.packtpub.rest.ch8.service.DepartmentResource.class);
        resources.add(com.packtpub.rest.ch8.service.EmployeeResource.class);
        resources.add(com.packtpub.rest.ch8.service.exception.ValidationExceptionMapper.class);

    }

}

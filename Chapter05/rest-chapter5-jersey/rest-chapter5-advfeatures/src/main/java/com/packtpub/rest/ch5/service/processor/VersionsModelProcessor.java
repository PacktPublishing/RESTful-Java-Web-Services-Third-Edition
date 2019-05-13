/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.service.processor;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.model.ModelProcessor;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceModel;

/**
 * A custom model processor that adds version API to all static resources during deployment, so that 
 * they respond to <resource>/version API calls at runtime returning the version details.
 * @author Jobinesh
 */
@Provider
public class VersionsModelProcessor implements ModelProcessor {

    @Override
    public ResourceModel processResourceModel(ResourceModel resourceModel, Configuration configuration) {

        // we get the resource model and we want to enhance each resource by adding support for returning
        //latest version of APIs
        ResourceModel.Builder newResourceModelBuilder = new ResourceModel.Builder(false);
        for (final Resource resource : resourceModel.getResources()) {
            // for each resource in the resource model we create a new builder which is based on the old resource
            final Resource.Builder resourceBuilder = Resource.builder(resource);

            // we add a new GET method to each resource for path template URI '/version'
            // Note that we should check whether the method does not yet exist to avoid failures
            resourceBuilder.addChildResource("version").addMethod(HttpMethod.GET)
                    .handledBy(new Inflector<ContainerRequestContext, String>() {
                        @Override
                        public String apply(ContainerRequestContext containerRequestContext) {
                            return "version : " + getLatestVersionforResource(resource);
                        }
                    }).produces(MediaType.TEXT_PLAIN)
                    .extended(true); // extended means: not part of core RESTful API

            // we add to the model new resource which is a combination of the old resource enhanced
            // by the 'version' URI support to know latest API version
            newResourceModelBuilder.addResource(resourceBuilder.build());
        }

        final ResourceModel newResourceModel = newResourceModelBuilder.build();
        // and we return new model
        return newResourceModel;

    }

    @Override
    public ResourceModel processSubResource(ResourceModel subResourceModel, Configuration configuration) {
        // we just return the original subResourceModel which means we do not want to do any modification
        // for this demo
        return subResourceModel;
    }

    /**
     * A dummy method returning a constance. This is just for demonstrating
     * ModelProcessor
     *
     * @param resource
     * @return
     */
    private String getLatestVersionforResource(Resource resource) {

        return "1.0";
    }
}

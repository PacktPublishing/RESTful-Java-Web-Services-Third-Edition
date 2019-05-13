/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.service.processor;


import java.util.ArrayList;

import java.util.List;


import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.model.ModelProcessor;
import org.glassfish.jersey.server.model.ResourceModel;
import org.glassfish.jersey.server.model.internal.ModelProcessorUtil;

/**
 *
 *
 * Model processors implementations can define binding priority to define the
 * order in which they are executed (processors with a lower priority is invoked
 * before processor with a higher priority). The highest possible priority
 * (Integer.MAX_VALUE) is used for model processor which enhance resource models
 * by the default OPTIONS method defined by JAX-RS specification and therefore
 * this priority should not be used.
 *
 * @author Jobinesh
 */
@Provider
public class JsonSchemaModelProcessorImpl implements ModelProcessor {

    //Creates a new instance of MediaType by parsing the supplied string.
    static final MediaType JSON_SCHEMA_TYPE
            = MediaType.valueOf("application/schema+json");
    private final List<ModelProcessorUtil.Method> methodList;

    public JsonSchemaModelProcessorImpl() {
        methodList = new ArrayList();
        //Method bean containing basic information about enhancing resource method.
        ModelProcessorUtil.Method method = new ModelProcessorUtil.Method("schema", HttpMethod.GET,
                MediaType.WILDCARD_TYPE, JSON_SCHEMA_TYPE,
                //Inflector handling the resource method
                JsonSchemaHandler.class);
        methodList.add(method);
    }

    /**
     * Process resourceModel during deploymnet and return the processed model.
     *
     * @param resourceModel
     * @param configuration
     * @return
     */
    @Override
    public ResourceModel processResourceModel(ResourceModel resourceModel, Configuration configuration) {
        //Enhance resourceModel by list of methods. The resourceModel is 
        //traversed and for each available endpoint URI in the model methods are added.

        return ModelProcessorUtil.enhanceResourceModel(resourceModel, false, methodList,
                true).build();
    }

    /**
     * Process subResourceModel during runtime which was returned a sub resource
     * locator.
     *
     * @param subResourceModel
     * @param configuration
     * @return
     */
    @Override
    public ResourceModel processSubResource(ResourceModel subResourceModel, Configuration configuration) {
        //Enhance resourceModel by list of methods. The resourceModel is 
        //traversed and for each available endpoint URI in the model methods are added.
        return ModelProcessorUtil.enhanceResourceModel(subResourceModel, true, methodList,
                true).build();
    }

}

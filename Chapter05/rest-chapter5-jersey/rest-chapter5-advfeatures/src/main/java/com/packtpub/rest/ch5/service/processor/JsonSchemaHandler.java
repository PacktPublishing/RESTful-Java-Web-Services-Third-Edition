/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.service.processor;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.RuntimeResource;

/**
 * Inflector handling the resource method.
 * @author Jobinesh
 */
public class JsonSchemaHandler
        implements Inflector<ContainerRequestContext, Response> {

    //Extensions to UriInfo.
    @Inject
    private ExtendedUriInfo extendedUriInfo;

    @Override
    public Response apply(ContainerRequestContext containerRequestContext) {

        //Find the resource that we are modifying to add scehma generation support           
        List<RuntimeResource> rtResrcList = extendedUriInfo.getMatchedRuntimeResources();
        List<ResourceMethod> resrcMthdList = rtResrcList.get(1).getResourceMethods();
        Class responseType = null;
        for (ResourceMethod resrcMthd : resrcMthdList) {
            //Get the first method marked for responding to HTTP GET  type and read its 
            //return type for generating JSON schema
            if ("GET".equals(resrcMthd.getHttpMethod())) {
                responseType = (Class) getDomainClass(resrcMthd.getInvocable().getResponseType());
                break;
            }
        }

        if (responseType == null) {
            throw new WebApplicationException("Cannot resolve type for schema generation");
        }

        try {
            //Generate JSON schema for the return type obtained in last step 
            JAXBContext context = (JAXBContext) JAXBContext.newInstance(responseType);
            StringWriter sw = new StringWriter();
            final StreamResult sr = new StreamResult(sw);
            context.generateJsonSchema(new SchemaOutputResolver() {
                @Override
                public Result createOutput(String namespaceUri, String suggestedFileName)
                        throws IOException {
                    return sr;
                }
            }, responseType);
            //Build reponse body
            return Response.ok().type(JsonSchemaModelProcessorImpl.JSON_SCHEMA_TYPE)
                    .entity(sw.toString()).build();
        } catch (JAXBException jaxb) {
            throw new WebApplicationException(jaxb);
        }
    }

    //Find out the Object type represented by genericType
    protected Class<?> getDomainClass(Type genericType) {
        if (null == genericType) {
            return Object.class;
        }
        if (genericType instanceof Class && genericType != JAXBElement.class) {
            Class<?> clazz = (Class<?>) genericType;
            if (clazz.isArray()) {
                return getDomainClass(clazz.getComponentType());
            }
            return clazz;
        } else if (genericType instanceof ParameterizedType) {
            Type type = ((ParameterizedType) genericType).getActualTypeArguments()[0];
            if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType();
                if (rawType == JAXBElement.class) {
                    return getDomainClass(type);
                }
            } else if (type instanceof WildcardType) {
                Type[] upperTypes = ((WildcardType) type).getUpperBounds();
                if (upperTypes.length > 0) {
                    Type upperType = upperTypes[0];
                    if (upperType instanceof Class) {
                        return (Class<?>) upperType;
                    }
                }
            } else if (JAXBElement.class == type) {
                return Object.class;
            }
            return (Class<?>) type;
        } else if (genericType instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) genericType;
            return getDomainClass(genericArrayType.getGenericComponentType());
        } else {
            return Object.class;
        }
    }
}

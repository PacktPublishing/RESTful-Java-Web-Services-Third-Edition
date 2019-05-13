/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.ext;

import com.packtpub.rest.ch4.model.Department;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.cellprocessor.constraint.NotNull;

/**
 * Contract for a provider that supports the conversion of a stream to a Java
 * type. A MessageBodyReader implementation may be annotated with Consumes to
 * restrict the media types for which it will be considered suitable. Providers
 * implementing MessageBodyReader contract must be either programmatically
 * registered in a JAX-RS runtime or must be annotated with @Provider annotation
 * to be automatically discovered by the JAX-RS runtime during a provider
 * scanning phase.
 *
 * @author Jobinesh
 */
@Provider
@Consumes("application/csv")
public class CSVMessageBodyReader implements MessageBodyReader<List<Department>> {

    private static final Logger logger = Logger.getLogger(CSVMessageBodyReader.class.getName());

    /**
     * Ascertain if the MessageBodyReader can produce an instance of a
     * particular type. The type parameter gives the class of the instance that
     * should be produced, the genericType parameter gives the
     * java.lang.reflect.Type of the instance that should be produced.
     *
     * @param type
     * @param genericType
     * @param annotations
     * @param mediaType
     * @return
     */
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Collection.class.isAssignableFrom(type);
    }

    /**
     * Read a type from the InputStream. In case the entity input stream is
     * empty, the reader is expected to either return a Java representation of a
     * zero-length entity or throw a NoContentException in case no zero-length
     * entity representation is defined for the supported Java type. A
     * NoContentException, if thrown by a message body reader while reading a
     * server request entity, is automatically translated by JAX-RS server
     * runtime into a BadRequestException wrapping the original
     * NoContentException and rethrown for a standard processing by the
     * registered exception mappers.
     *
     * @param type
     * @param genericType
     * @param annotations
     * @param mediaType
     * @param httpHeaders
     * @param entityStream
     * @return
     * @throws IOException
     * @throws WebApplicationException
     */
    @Override
    public List readFrom(Class<List<Department>> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {

        ArrayList list = new ArrayList();
        final CellProcessor[] processors = new CellProcessor[]{
            new NotNull(new ParseShort()), // departmentId
            new NotNull(), // departmentName
            new NotNull(new ParseShort()), // locationId
            new Optional(new ParseInt()) //managerId
        };
        ICsvBeanReader beanReader = new CsvBeanReader(new InputStreamReader(entityStream), CsvPreference.STANDARD_PREFERENCE);
        String[] header = beanReader.getHeader(false);
        Object obj = null;
        while ((obj = beanReader.read(Department.class, header, processors)) != null) {
            list.add(obj);
            logger.log(Level.INFO, obj.toString());
        }

        return list;
    }

}

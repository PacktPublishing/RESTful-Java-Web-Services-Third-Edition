/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.ext;

import com.packtpub.rest.ch4.model.Department;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;


/**
 * Contract for a provider that supports the conversion of a Java type to a
 * stream. To add a MessageBodyWriter implementation, annotate the
 * implementation class with @Provider. A MessageBodyWriter implementation may
 * be annotated with Produces to restrict the media types for which it will be
 * considered suitable.
 *
 * @author Jobinesh
 */
@Provider
@Produces("application/csv")
public class CSVMessageBodyWriter implements MessageBodyWriter<List<Department>> {

    private static final Logger logger = Logger.getLogger(CSVMessageBodyWriter.class.getName());

    /**
     * Ascertain if the MessageBodyWriter supports a particular type.
     *
     * @param type
     * @param genericType
     * @param annotations
     * @param mediaType
     * @return
     */
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        //Is this MessageBodyWriter implemntation is suitable for 
        //serilizing current object type?
        return (List.class.isAssignableFrom(type));
    }

    /**
     * Called before writeTo to ascertain the length in bytes of the serialized
     * form of t. A non-negative return value is used in a HTTP Content-Length
     * header.
     *
     * @deprecated
     * @param t
     * @param type
     * @param genericType
     * @param annotations
     * @param mediaType
     * @return
     */
    @Override
    public long getSize(List<Department> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
        return 0;
    }

    /**
     * Write a type to an HTTP response. The response header map is mutable but
     * any changes must be made before writing to the output stream since the
     * headers will be flushed prior to writing the response body.
     *
     * @param dataList
     * @param type
     * @param genericType
     * @param annotations
     * @param mediaType
     * @param httpHeaders
     * @param entityStream
     * @throws IOException
     * @throws WebApplicationException
     */
    @Override
    public void writeTo(List<Department> dataList, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        //CsvBeanWriter is an open source, which writes a CSV file by
        // mapping each field on the bean to a column in the CSV file 
        //(using the supplied name mapping).
        ICsvBeanWriter writer = new CsvBeanWriter(
                new PrintWriter(entityStream), CsvPreference.STANDARD_PREFERENCE);
        //No data then retrun
        // httpHeaders.add("Content-Type", "application/csv");
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        //Get the first object, iterate over the properties and
        //use these properties for defining columns headers in CSV
        //{"departmentId","departmentName","managerId","locationId"}
        String[] nameMapping = getHeader(dataList.get(0));
        logger.log(Level.INFO, nameMapping.toString());
        //CsvBeanWriter writes the header with the property names  
        writer.writeHeader(nameMapping);
        for (Object p : dataList) {
            //Write each row
            writer.write(p, nameMapping);
        }
        writer.close();
    }

    /**
     * This method return the bean property names as String[]
     *
     * @param obj
     * @return String[]
     */
    private String[] getHeader(Object obj) {
        ArrayList<String> headers = new ArrayList<String>();

        try {
            BeanInfo info = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] pds = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                //Skip getClass() property
                if (!pd.getName().equals("class")) {
                    headers.add(pd.getDisplayName());
                }
                logger.log(Level.INFO, "Field :" + pd.getName());
            }

        } catch (IntrospectionException ex) {
            Logger.getLogger(CSVMessageBodyWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return headers.toArray(new String[headers.size()]);
    }

}

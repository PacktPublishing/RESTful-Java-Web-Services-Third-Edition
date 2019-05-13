/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch9.odata.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;

/**
 * Perform CRUD operations on Department entity using OData API
 *
 * @author Jobinesh
 */
public class ODataDepartmentServiceClient {

    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_DELETE = "DELETE";

    public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HTTP_HEADER_ACCEPT = "Accept";

    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_ATOM_XML = "application/atom+xml";
    public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    public static final String METADATA = "$metadata";
    public static final String INDEX = "/index.jsp";
    public static final String SEPARATOR = "/";

    public static final boolean PRINT_RAW_CONTENT = true;

    public static void main(String[] paras) throws Exception {
        ODataDepartmentServiceClient app = new ODataDepartmentServiceClient();

        String serviceUrl = "http://localhost:12255/rest-chapter9-odata-service/odata";

        String usedFormat = APPLICATION_JSON;

        print("\n----- Read Edm ------------------------------");
        Edm edm = app.readEdm(serviceUrl);
        print("Read default EntityContainer: " + edm.getDefaultEntityContainer().getName());

        print("\n----- Read Feed ------------------------------");
        ODataFeed feed = app.readFeed(edm, serviceUrl, usedFormat, "Departments");

        print("Read: " + feed.getEntries().size() + " entries: ");
        for (ODataEntry entry : feed.getEntries()) {
            print("##########");
            print("Entry:\n" + prettyPrint(entry));
            print("##########");
        }

        print("\n----- Read Entry ------------------------------");
        ODataEntry entry = app.readEntry(edm, serviceUrl, usedFormat, "Departments", "10");
        print("Single Entry:\n" + prettyPrint(entry));

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("DepartmentId", new Integer(700));
        data.put("DepartmentName", "R and D");
        Map<String, Object> employees = new HashMap<String, Object>();
        data.put("EmployeeDetails", employees);
        print("\n----- Create Entry ------------------------------");
        ODataEntry createdEntry = app.createEntry(edm, serviceUrl, usedFormat, "Departments", data);
        print("Created Entry:\n" + prettyPrint(createdEntry));

        print("\n----- Update Entry ------------------------------");
        data.put("LocationId", new Integer(1700));
        data.put("ManagerId", new Integer(101));
        app.updateEntry(edm, serviceUrl, usedFormat, "Departments", "700", data);
        ODataEntry updatedEntry = app.readEntry(edm, serviceUrl, usedFormat, "Departments", "700");
        print("Updated Entry successfully:\n" + prettyPrint(updatedEntry));

        //
        print("\n----- Delete Entry ------------------------------");
        HttpStatusCodes statusCode = app.deleteEntry(serviceUrl, "Departments", "700");
        print("Deletion of Entry was successfully: " + statusCode.getStatusCode() + ": " + statusCode.getInfo());

    }

    private static void print(String content) {
        System.out.println(content);
    }

    private static String prettyPrint(ODataEntry createdEntry) {
        return prettyPrint(createdEntry.getProperties(), 0);
    }

    private static String prettyPrint(Map<String, Object> properties, int level) {
        StringBuilder b = new StringBuilder();
        Set<Entry<String, Object>> entries = properties.entrySet();

        for (Entry<String, Object> entry : entries) {
            intend(b, level);
            b.append(entry.getKey()).append(": ");
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = prettyPrint((Map<String, Object>) value, level + 1);
                b.append(value).append("\n");
            } else if (value instanceof Calendar) {
                Calendar cal = (Calendar) value;
                value = SimpleDateFormat.getInstance().format(cal.getTime());
                b.append(value).append("\n");
            } else if (value instanceof ODataDeltaFeed) {
                ODataDeltaFeed feed = (ODataDeltaFeed) value;
                List<ODataEntry> inlineEntries = feed.getEntries();
                b.append("{");
                for (ODataEntry oDataEntry : inlineEntries) {
                    value = prettyPrint((Map<String, Object>) oDataEntry.getProperties(), level + 1);
                    b.append("\n[\n").append(value).append("\n],");
                }
                b.deleteCharAt(b.length() - 1);
                intend(b, level);
                b.append("}\n");
            } else {
                b.append(value).append("\n");
            }
        }
        // remove last line break
        b.deleteCharAt(b.length() - 1);
        return b.toString();
    }

    private static void intend(StringBuilder builder, int intendLevel) {
        for (int i = 0; i < intendLevel; i++) {
            builder.append("  ");
        }
    }

    public void generateSampleData(String serviceUrl) throws MalformedURLException, IOException {
        String url = serviceUrl.substring(0, serviceUrl.lastIndexOf(SEPARATOR));
        HttpURLConnection connection = initializeConnection(url + INDEX, APPLICATION_FORM, HTTP_METHOD_POST);
        String content = "genSampleData=true";
        connection.getOutputStream().write(content.getBytes());
        print("Generate response: " + checkStatus(connection));
        connection.disconnect();
    }

    public Edm readEdm(String serviceUrl) throws IOException, ODataException {
        InputStream content = execute(serviceUrl + SEPARATOR + METADATA, APPLICATION_XML, HTTP_METHOD_GET);
        return EntityProvider.readMetadata(content, false);
    }

    public ODataFeed readFeed(Edm edm, String serviceUri, String contentType, String entitySetName)
            throws IOException, ODataException {
        EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
        String absolutUri = createUri(serviceUri, entitySetName, null);

        InputStream content = execute(absolutUri, contentType, HTTP_METHOD_GET);
        return EntityProvider.readFeed(contentType,
                entityContainer.getEntitySet(entitySetName),
                content,
                EntityProviderReadProperties.init().build());
    }

    public ODataEntry readEntry(Edm edm, String serviceUri, String contentType, String entitySetName, String keyValue)
            throws IOException, ODataException {
        return readEntry(edm, serviceUri, contentType, entitySetName, keyValue, null);
    }

    public ODataEntry readEntry(Edm edm, String serviceUri, String contentType,
            String entitySetName, String keyValue, String expandRelationName)
            throws IOException, ODataException {
        // working with the default entity container
        EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
        // create absolute uri based on service uri, entity set name with its key property value and optional expanded relation name
        String absolutUri = createUri(serviceUri, entitySetName, keyValue, expandRelationName);

        InputStream content = execute(absolutUri, contentType, HTTP_METHOD_GET);

        return EntityProvider.readEntry(contentType,
                entityContainer.getEntitySet(entitySetName),
                content,
                EntityProviderReadProperties.init().build());
    }

    private InputStream logRawContent(String prefix, InputStream content, String postfix) throws IOException {
        if (PRINT_RAW_CONTENT) {
            byte[] buffer = streamToArray(content);
            print(prefix + new String(buffer) + postfix);
            return new ByteArrayInputStream(buffer);
        }
        return content;
    }

    private byte[] streamToArray(InputStream stream) throws IOException {
        byte[] result = new byte[0];
        byte[] tmp = new byte[8192];
        int readCount = stream.read(tmp);
        while (readCount >= 0) {
            byte[] innerTmp = new byte[result.length + readCount];
            System.arraycopy(result, 0, innerTmp, 0, result.length);
            System.arraycopy(tmp, 0, innerTmp, result.length, readCount);
            result = innerTmp;
            readCount = stream.read(tmp);
        }
        stream.close();
        return result;
    }

    public ODataEntry createEntry(Edm edm, String serviceUri, String contentType,
            String entitySetName, Map<String, Object> data) throws Exception {
        String absolutUri = createUri(serviceUri, entitySetName, null);
        return writeEntity(edm, absolutUri, entitySetName, data, contentType, HTTP_METHOD_POST);
    }

    public void updateEntry(Edm edm, String serviceUri, String contentType, String entitySetName,
            String id, Map<String, Object> data) throws Exception {
        String absolutUri = createUri(serviceUri, entitySetName, id);
        writeEntity(edm, absolutUri, entitySetName, data, contentType, HTTP_METHOD_PUT);
    }

    public HttpStatusCodes deleteEntry(String serviceUri, String entityName, String id) throws IOException {
        String absolutUri = createUri(serviceUri, entityName, id);
        HttpURLConnection connection = connect(absolutUri, APPLICATION_XML, HTTP_METHOD_DELETE);
        return HttpStatusCodes.fromStatusCode(connection.getResponseCode());
    }

    private ODataEntry writeEntity(Edm edm, String absolutUri, String entitySetName,
            Map<String, Object> data, String contentType, String httpMethod)
            throws EdmException, MalformedURLException, IOException, EntityProviderException, URISyntaxException {

        HttpURLConnection connection = initializeConnection(absolutUri, contentType, httpMethod);

        EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
        EdmEntitySet entitySet = entityContainer.getEntitySet(entitySetName);
        URI rootUri = new URI(entitySetName);

        EntityProviderWriteProperties properties = EntityProviderWriteProperties.serviceRoot(rootUri).build();
        // serialize data into ODataResponse object
        ODataResponse response = EntityProvider.writeEntry(contentType, entitySet, data, properties);
        // get (http) entity which is for default Olingo implementation an InputStream
        Object entity = response.getEntity();
        if (entity instanceof InputStream) {
            byte[] buffer = streamToArray((InputStream) entity);
            // just for logging
            String content = new String(buffer);
            print(httpMethod + " request on uri '" + absolutUri + "' with content:\n  " + content + "\n");
            //
            connection.getOutputStream().write(buffer);
        }

        // if a entity is created (via POST request) the response body contains the new created entity
        ODataEntry entry = null;
        HttpStatusCodes statusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
        if (statusCode == HttpStatusCodes.CREATED) {
            // get the content as InputStream and de-serialize it into an ODataEntry object
            InputStream content = connection.getInputStream();
            content = logRawContent(httpMethod + " request on uri '" + absolutUri + "' with content:\n  ", content, "\n");
            entry = EntityProvider.readEntry(contentType,
                    entitySet, content, EntityProviderReadProperties.init().build());
        }
        print(connection.getResponseMessage());
        //
        connection.disconnect();

        return entry;
    }

    private HttpStatusCodes checkStatus(HttpURLConnection connection) throws IOException {
        HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
        if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) {
            throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " " + httpStatusCode.toString());
        }
        return httpStatusCode;
    }

    private String createUri(String serviceUri, String entitySetName, String id) {
        return createUri(serviceUri, entitySetName, id, null);
    }

    private String createUri(String serviceUri, String entitySetName, String id, String expand) {
        final StringBuilder absolutUri = new StringBuilder(serviceUri).append(SEPARATOR).append(entitySetName);
        if (id != null) {
            absolutUri.append("(").append(id).append(")");
        }
        if (expand != null) {
            absolutUri.append("/?$expand=").append(expand);
        }
        return absolutUri.toString();
    }

    private InputStream execute(String relativeUri, String contentType, String httpMethod) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);

        connection.connect();
        checkStatus(connection);

        InputStream content = connection.getInputStream();
        content = logRawContent(httpMethod + " request on uri '" + relativeUri + "' with content:\n  ", content, "\n");
        return content;
    }

    private HttpURLConnection connect(String relativeUri, String contentType, String httpMethod) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);

        connection.connect();
        checkStatus(connection);

        return connection;
    }

    private HttpURLConnection initializeConnection(String absolutUri, String contentType, String httpMethod)
            throws MalformedURLException, IOException {
        URL url = new URL(absolutUri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(httpMethod);
        connection.setRequestProperty(HTTP_HEADER_ACCEPT, contentType);
        if (HTTP_METHOD_POST.equals(httpMethod) || HTTP_METHOD_PUT.equals(httpMethod)) {
            connection.setDoOutput(true);
            connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);
        }

        return connection;
    }
}

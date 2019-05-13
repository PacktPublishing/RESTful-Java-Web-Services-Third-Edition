/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.client;

import com.packtpub.rest.ch5.model.Employee;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ChunkedInput;

/**
 * Jersey REST client generated for REST resource:EmployeeResource This class
 * demonstrates usages of ChunkedInput. Change the BASE_URI to reflect your
 * server settings.
 *
 * @author Jobinesh
 */
public class EmployeeResourceClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:12255/rest-chapter5-advfeatures/webresources";

    public EmployeeResourceClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("employees");
    }

    public <T> T findAllInChunk(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("chunk");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public static void main(String arg[]) {
        EmployeeResourceClient clnt = new EmployeeResourceClient();
        clnt.findAllEmployeesInChunk();
        clnt.close();
    }

    public void findAllEmployeesInChunk() {
        WebTarget resource = webTarget;
        resource = resource.path("chunk");
        Response response = resource.request().get();
        final ChunkedInput<List<Employee>> chunks = response.readEntity(new GenericType<ChunkedInput<List<Employee>>>() {
        });

        List<Employee> chunk;
        while ((chunk = chunks.read()) != null) {
            System.out.println("Next chunk received: " + chunk);
        }
        close();
    }

    public void close() {
        client.close();
    }

}

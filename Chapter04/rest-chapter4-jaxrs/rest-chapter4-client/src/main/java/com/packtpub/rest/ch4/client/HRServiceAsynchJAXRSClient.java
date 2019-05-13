/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.client;

import com.packtpub.rest.ch4.model.Department;
import java.util.List;
import java.util.concurrent.Future;
import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 * JAX-RS Asynchronous REST client generated for REST resource:HRService [hr]<br>
 *
 *
 * @author Jobinesh
 */
public class HRServiceAsynchJAXRSClient {

    private static final String BASE_URI = "http://localhost:12255/rest-chapter4-service/webresources";

    public HRServiceAsynchJAXRSClient() {
    }

    public static void main(String arg[]) {
        try {
            new HRServiceAsynchJAXRSClient().findAllDepartmnetsInAsynchWithCallback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void findAllDepartmnetsInAsynch() throws Exception {

        Client client = javax.ws.rs.client.ClientBuilder.newClient();
        WebTarget webTarget = client.target(BASE_URI).path("hr").path("departments");
        AsyncInvoker asyncInvoker = webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).async();
        
        Future<Response> responseFuture = asyncInvoker.get();
        System.out.println("Request is being processed asynchronously.");
        Response response = responseFuture.get();
        System.out.println("Response: " + response);
        List<Department> depts = response.readEntity(new GenericType<List<Department>>() {
        });
        System.out.println("depts: " + depts);
        response.close();
        client.close();
        // List<Department> depts = response.readEntity(new GenericType<List<Department>>() { });
        // get() waits for the response to be ready
        // System.out.println("Response received." + depts);

    }

    public void findAllDepartmnetsInAsynchWithCallback() throws Exception {

        final Client client = javax.ws.rs.client.ClientBuilder.newClient();
        WebTarget webTarget = client.target(BASE_URI).path("hr").path("departments");
        AsyncInvoker asyncInvoker = webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).async();

        System.out.println("Request is being processed asynchronously.");

        Future<List<Department>> entity = asyncInvoker.get(new InvocationCallback<List<Department>>() {
            @Override
            public void completed(List<Department> response) {
                System.out.println("Response entity '" + response + "' received.");
                client.close();
            }

            @Override
            public void failed(Throwable throwable) {
                System.out.println("Invocation failed.");
                throwable.printStackTrace();
            }
        });
        System.out.println("Request is being processed asynchronously." + entity.get());

    }
}

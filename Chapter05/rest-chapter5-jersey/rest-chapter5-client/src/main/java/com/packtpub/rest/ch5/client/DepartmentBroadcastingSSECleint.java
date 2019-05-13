/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 * This class demonstrates client API for Server Sent Events  client 
 * @author Jobinesh
 */
public class DepartmentBroadcastingSSECleint {

   
    public void sseCleint() {
        Client client = ClientBuilder.newBuilder()
                .register(SseFeature.class).build();
        WebTarget target = client.target("http://localhost:12255/rest-chapter5-advfeatures/webresources/departments/events");

        EventInput eventInput = target.request().get(EventInput.class);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();
            if (inboundEvent == null) {
                // connection has been closed
                break;
            }
            System.out.println(inboundEvent.getName() + "; "
                    + inboundEvent.readData(String.class));
        }
    }

   

    public static void main(String[] arg) {
        new DepartmentBroadcastingSSECleint().sseCleint();
    }
}

/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.service.monitor;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

/**
 * Non-registrable provider that listens to request events. The implementation
 * of the interface will be called for request events when they occur. The
 * provider cannot be registered as a standard JAX-RS or Jersey provider. The
 * instance of the RequestEventListener must be returned from the
 * ApplicationEventListener.onRequest(RequestEvent). This will register the
 * instance for listening of request events for one particular request. Once the
 * processing of the request is finished, the instance will be ignored by the
 * Jersey runtime and not used for processing of further requests.
 *
 * @author Jobinesh
 */
public class RequestEventListenerImpl implements RequestEventListener {

    private static final Logger logger = Logger.getLogger(RequestEventListenerImpl.class.getName());
    
    private int requestNumber;
   
    public RequestEventListenerImpl(int id)
    {
        this.requestNumber = id;
    }
    
    @Override
    public void onEvent(RequestEvent event) {
        logger.info("RequestEventListenerImpl::onEvent");
        switch (event.getType()) {
            case RESOURCE_METHOD_START:
                logger.log(Level.INFO, "Resource {0} method {1} started for request {2}", new Object[]{event.getUriInfo().getRequestUri().getPath(),event.getUriInfo().getMatchedResourceMethod()
                        .getHttpMethod(), requestNumber});
                break;
            case FINISHED:
                logger.log(Level.INFO, "Request {0} finished. ", requestNumber);
                break;
        }
    }

}

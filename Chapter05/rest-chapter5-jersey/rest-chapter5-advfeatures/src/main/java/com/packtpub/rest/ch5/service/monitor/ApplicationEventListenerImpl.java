/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.service.monitor;

import java.util.logging.Logger;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

/**
 * Jersey specific provider that listens to application events. The
 * implementation of this interface will be called for two kind of events:
 * application events and request events. This interface will listen to all
 * application event types but only to first request event which is the
 * RequestEvent.Type.START. On this event the application event listener can
 * decide whether it will listen to the request and return request event
 * listener for listening to further request events. }
 *
 * @author Jobinesh
 */
@Provider
public class ApplicationEventListenerImpl implements ApplicationEventListener {

    private static final Logger logger = Logger.getLogger(ApplicationEventListenerImpl.class.getName());
    
    private int requestCount = 0;

    @Override
    public void onEvent(ApplicationEvent ae) {
        logger.info("ApplicationEventListenerImpl::onEvent");
        switch (ae.getType()) {
            case INITIALIZATION_START:
                logger.info("INITIALIZATION_START");
                break;
            case INITIALIZATION_APP_FINISHED:
                logger.info("INITIALIZATION_APP_FINISHED");
                break;
            case INITIALIZATION_FINISHED:
                logger.info("INITIALIZATION_FINISHED");
                break;
            case RELOAD_FINISHED:
                logger.info("RELOAD_FINISHED");
                break;
            case DESTROY_FINISHED:
                logger.info("DESTROY_FINISHED");

        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent re) {
        requestCount++;
        logger.fine("ApplicationEventListenerImpl::onRequest");
        return new RequestEventListenerImpl(requestCount);
    }

}

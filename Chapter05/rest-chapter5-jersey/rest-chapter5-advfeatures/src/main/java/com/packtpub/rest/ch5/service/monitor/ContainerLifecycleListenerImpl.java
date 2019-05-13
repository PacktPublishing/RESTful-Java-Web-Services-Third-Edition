/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch5.service.monitor;

import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

/**
 * Classes implementing this contract receive container life-cycle notification
 * events.
 *
 * @author Jobinesh
 */
@Singleton
@Provider
public class ContainerLifecycleListenerImpl implements ContainerLifecycleListener {

    private static final Logger logger = Logger.getLogger(ContainerLifecycleListenerImpl.class.getName());

    @Override
    public void onStartup(Container cntnr) {
        logger.info("ContainerLifecycleListenerImpl::onStartup");
    }

    @Override
    public void onReload(Container cntnr) {
        logger.info("ContainerLifecycleListenerImpl::onReload");
    }

    @Override
    public void onShutdown(Container cntnr) {
        logger.info("ContainerLifecycleListenerImpl::onShutdown");
    }

}

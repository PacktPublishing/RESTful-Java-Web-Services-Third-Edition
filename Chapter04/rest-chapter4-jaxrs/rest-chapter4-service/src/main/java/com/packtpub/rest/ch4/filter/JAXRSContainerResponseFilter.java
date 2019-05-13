/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * An example for ContainerResponseFilter
 * This is Cross-origin resource sharing (CORS) filter which enables cross domain access
 * @author Jobinesh
 */
@Provider
public class JAXRSContainerResponseFilter implements ContainerResponseFilter {

    private static final Logger logger = Logger.getLogger(JAXRSContainerResponseFilter.class.getName());

    @Override
    public void filter(final ContainerRequestContext requestContext,
            final ContainerResponseContext cres) throws IOException {
        logger.log(Level.INFO, "ContainerRequestContext:{0}", requestContext);

        cres.getHeaders().add("Access-Control-Allow-Origin", "*");
        cres.getHeaders().add("Access-Control-Allow-Headers", "*");
        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        // cres.getHeaders().add("Access-Control-Max-Age", "1209600");
    }

}

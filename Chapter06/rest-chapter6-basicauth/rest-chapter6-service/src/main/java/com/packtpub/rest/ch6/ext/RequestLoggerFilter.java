/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.ext;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

/**
 * This filter will be registered dynamically from DynamicFeatureRegister
 *
 * @author Jobinesh
 */

public class RequestLoggerFilter implements ContainerRequestFilter {

    private static final Logger logger = Logger.getLogger(RequestLoggerFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logger.log(Level.INFO, "----Requested---{0}", requestContext.getUriInfo().toString());
    }

}

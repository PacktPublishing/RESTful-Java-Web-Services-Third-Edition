/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.validation;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Sub-classed from WebApplicationException This exception may be thrown by a
 * resource method, provider or StreamingOutput implementation if a specific
 * HTTP error response needs to be produced. Only effective if thrown prior to
 * the response being committed.
 *
 * @author Jobinesh
 */
public class DepartmentNotFoundWebAppException extends WebApplicationException {

    /**
     * Create a HTTP 404 (Not Found) exception.
     */
    public DepartmentNotFoundWebAppException() {
        super(Response.Status.NOT_FOUND);
    }

    /**
     * Create a HTTP 404 (Not Found) exception.
     *
     * @param message
     */
    public DepartmentNotFoundWebAppException(String message) {
        super(Response.status(Status.NOT_FOUND).entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}

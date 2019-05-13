/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.validation;

import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps DeprtmentNotFoundBusinessException exceptions to HTTP Response code.
 *
 * @author Jobinesh
 */
@Provider
public class DepartmentNotFoundExceptionMapper implements ExceptionMapper<DeprtmentNotFoundBusinessException> {

    private static final Logger logger = Logger.getLogger(DepartmentNotFoundExceptionMapper.class.getName());

    public DepartmentNotFoundExceptionMapper() {
        logger.info("-----DepartmentNotFoundExceptionMapper-----------");
    }

    @Override
    public Response toResponse(DeprtmentNotFoundBusinessException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }

}

/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch8.service.exception;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * {@link ExceptionMapper} for {@link ValidationException}.
 * <p>
 * Send a {@link ViolationReport} in {@link Response} in addition to HTTP
 * 400/500 status code. Supported media types are: {@code application/json} /
 * {@code application/xml} (if appropriate provider is registered on server).
 * </p>
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    /**
     * Map an exception to a Response. Returning null results in a
     * Response.Status.NO_CONTENT response. Throwing a runtime exception results
     * in a Response.Status.INTERNAL_SERVER_ERROR response.
     *
     * @param exception
     * @return
     */
    @Override
    public Response toResponse(ValidationException exception) {

        if (exception instanceof ConstraintViolationException) {
            return buildResponse(unwrapException((ConstraintViolationException) exception), MediaType.TEXT_PLAIN, Status.BAD_REQUEST);
        }

        return buildResponse(unwrapException(exception), MediaType.TEXT_PLAIN, Status.BAD_REQUEST);
    }

    protected Response buildResponse(Object entity, String mediaType, Status status) {
        ResponseBuilder builder = Response.status(status).entity(entity);
        builder.type(MediaType.TEXT_PLAIN);
        builder.header("validation-exception", "true");
        return builder.build();
    }

    protected String unwrapException(ConstraintViolationException exception) {
        StringBuffer sb = new StringBuffer();
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        for (ConstraintViolation violation : violations) {
            sb.append(violation.getMessage());
        }

        return sb.toString();
    }

    protected String unwrapException(Throwable t) {
        StringBuffer sb = new StringBuffer();
        doUnwrapException(sb, t);
        return sb.toString();
    }

    private void doUnwrapException(StringBuffer sb, Throwable t) {
        if (t == null) {
            return;
        }
        sb.append(t.getMessage()).append("  ");
        if (t.getCause() != null && t != t.getCause()) {

            doUnwrapException(sb, t.getCause());

        }
    }

}

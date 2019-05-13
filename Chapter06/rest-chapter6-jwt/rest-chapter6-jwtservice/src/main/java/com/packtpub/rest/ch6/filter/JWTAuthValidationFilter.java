/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.filter;

import com.packtpub.rest.ch6.util.JWTAuthHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Performs verification of supplied JWT Token
 * 
 * @author Balachandar
 */
@Provider
@JWTTokenRequired
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthValidationFilter implements ContainerRequestFilter {

    private static final Logger logger = Logger.getLogger(JWTAuthValidationFilter.class.getName());    
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //Step-1: Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        logger.log(Level.INFO, "Authorization Header : {0}", authorizationHeader);

        //Step-2: Check if the Authorization header contains the Token
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.log(Level.SEVERE, "Invalid Authorization Header : {0}", authorizationHeader);
            throw new NotAuthorizedException("Authorization header is missing");
        }

        //Step-3: Extract the token from the Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        //Step-4: Validate the token. If invalid, set UNAUTHORIZED response status
        if(!JWTAuthHelper.isValidToken(token)) {
            
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}

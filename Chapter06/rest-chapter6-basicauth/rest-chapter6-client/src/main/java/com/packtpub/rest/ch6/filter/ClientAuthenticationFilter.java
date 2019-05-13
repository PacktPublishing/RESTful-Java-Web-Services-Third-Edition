/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Adds the Basic Authentication Header
 * 
 * @author Balachandar
 */
public class ClientAuthenticationFilter implements ClientRequestFilter {

    private final String user;
    private final String password;

    public ClientAuthenticationFilter(String user,
            String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        MultivaluedMap<String, Object> headers
                = requestContext.getHeaders();
        final String basicAuthentication
                = getBasicAuthentication();

        headers.add("Authorization", basicAuthentication);
    }

    //Return BASE64 encoded user name and password
    private String getBasicAuthentication() {
        String token = this.user + ":" + this.password;
        try {
            byte[] encoded
                    = Base64.getEncoder().encode(
                            token.getBytes("UTF-8"));
            return "BASIC " + new String(encoded);
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(
                    "Cannot encode with UTF-8", ex);
        }
    }
}

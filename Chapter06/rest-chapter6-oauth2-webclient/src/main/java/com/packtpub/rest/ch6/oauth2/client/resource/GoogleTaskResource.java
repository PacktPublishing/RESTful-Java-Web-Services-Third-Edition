/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2015 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.packtpub.rest.ch6.oauth2.client.resource;

import com.packtpub.rest.ch6.oauth2.client.OAuthServiceContext;
import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import javax.servlet.ServletContext;

import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.glassfish.jersey.client.oauth2.OAuth2CodeGrantFlow;
import org.glassfish.jersey.client.oauth2.OAuth2FlowGoogleBuilder;

import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Inspired by Jersey example: oauth2-client-google-webapp 
 * GoogleTaskResource returns Google tasks that was queried by the client OAuthContextSetupResource
 * takes client to this REST API.
 */
@Path("tasks")
public class GoogleTaskResource {
    
    private static final String GOOGLE_TASKS_BASE_URI = "https://www.googleapis.com/tasks/v1/";

    @Context
    private UriInfo uriInfo;
    @Context
    private ServletContext servletContext;

    @GET
    // @Template(name = "/tasks.mustache")
    @Produces("text/plain")
    public Response getTasks() {
        //Gets the oauthServiceContext set via the OAuthContextSetupResource API
        OAuthServiceContext oauthServiceContext = OAuthServiceContext.getInstnace(null);
        // check is the client has intitialzed oauthServiceContext with ClientIdentifier
        if (oauthServiceContext.getClientIdentifier() == null) {
            //Takes client to starting page as there is no ClientIdentifier set
            final URI uri = UriBuilder.fromUri(servletContext.getContextPath())
                    .path("/index.html")
                    .build();
            return Response.seeOther(uri).build();
        }
        // Get the access token by contacting the Authorization Server(API provider)
        if (oauthServiceContext.getAccessToken() == null) {
            return googleAuthRedirect();
        }
        // We have already an access token. Query the data from Google API.
        final Client client = oauthServiceContext.getFlow().getAuthorizedClient();
        return getTasksResponse(client);
    }

    /**
     * Takes user to Google Authorization URI.
     *
     * @return redirect response to Google Tasks API auth consent request
     */
    private Response googleAuthRedirect() {
        //Builds the redirect URI which will be used by the authorization server 
        //to take user back to the client application on approval by the resource owner
        final String redirectURI = UriBuilder.fromUri(uriInfo.getBaseUri())
                .path("oauth2/authorize").build().toString();

        OAuthServiceContext oauthServiceContext = OAuthServiceContext.getInstnace(null);
        final OAuth2CodeGrantFlow flow = OAuth2ClientSupport.googleFlowBuilder(oauthServiceContext.getClientIdentifier(),
                redirectURI,
                "https://www.googleapis.com/auth/tasks.readonly")
                .prompt(OAuth2FlowGoogleBuilder.Prompt.CONSENT).build();

        oauthServiceContext.setFlow(flow);

        // Start the flow
        final String googleAuthURI = flow.start();

        // Redirect user to Google Authorization URI.
        return Response.seeOther(UriBuilder.fromUri(googleAuthURI).build()).build();
    }

    /**
     * Gets Tasks lists by calling
     * https://www.googleapis.com/tasks/v1/users/@me/lists
     *
     * @param client Client configured for authentication with access token.
     * @return Google task lists
     */
    private Response getTasksResponse(final Client client) {
        client.register(JacksonFeature.class);
        

        final WebTarget baseTarget = client.target(GOOGLE_TASKS_BASE_URI);
        final Response response = baseTarget.path("users/@me/lists").request().get();

        String result = "";
        switch (response.getStatus()) {
            case 401: //Response.Status.UNAUTHORIZED
                OAuthServiceContext.getInstnace(null).setAccessToken(null);
                return googleAuthRedirect();
            case 200: //Response.Status.OK
                result = response.readEntity(String.class);
                break;

        }

        return Response.ok(result).build();
    }

}

/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.service;

import com.packtpub.rest.ch6.filter.JWTTokenRequired;
import com.packtpub.rest.ch6.util.JWTAuthHelper;
import com.packtpub.rest.ch6.model.Department;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * A REST resource class which fetch enterprise department details
 * for JWT Authentication token
 * 
 * @author Balachandar
 */
@Stateless
@Path("hr")
public class HRService {

    @PersistenceContext(unitName = "com.packtpub_rest-chapter6-service_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    
    @Context
    private UriInfo uriInfo;
    
    private static final Logger logger = Logger.getLogger(HRService.class.getName());

    public HRService() {

    }
    
    /**
    * Authenticates the user and generates JWT Token
    *
    * @return JWT Token in the Response
    */
    @POST
    @Path("/login")
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("login") String login,
                                     @FormParam("password") String password) {
        try {
            // TO-DO Authenticate the user using the credentials provided
            //for brevity authenticaiton of credentials is not included.
            //Also as best practice authentication service will be hosted on 
            //dedicated auth server.
            
            // Issue a token for the user
            String token = JWTAuthHelper.issueToken(login,uriInfo.getAbsolutePath().toString());
            // Return the token in the response HTTP Authorization Header
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }    

    /**
    * Get the department details of the specified department only
    * when valid authentication token was provided.
    *
    * @return Department
    */    
    @GET
    @Path("departments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenRequired
    public Department findDepartment(@PathParam("id") @Min(value = 0, message = "Department Id must be a positive value") Short id, @Context Request request) {

        Department department = entityManager.find(Department.class, id);
        return department;
    }
}

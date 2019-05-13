/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.service;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * This class that defines the subresource concept
 *
 * @author Balachandar
 */
@Path("org")
public class OrganisationResource {

    @Path("{resourcetype}")
    public Class getResource(@PathParam("resourcetype") String resourceId) {
        if (resourceId.equals("department")) {
            return DepartmentResource.class;
        } else {
            return EmployeeResource.class;
        } 
    }
}

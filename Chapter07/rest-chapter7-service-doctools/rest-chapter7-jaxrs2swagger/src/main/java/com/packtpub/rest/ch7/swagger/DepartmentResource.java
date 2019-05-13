/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch7.swagger;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.Authorization;
import com.wordnik.swagger.annotations.AuthorizationScope;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Department resource class.  
 * 
 * @author Jobinesh
 */
@Stateless
@Path("departments")
@Api(value = "/departments", description = "Get departments details", authorizations = {
    @Authorization(value = "hrapp_auth",
            scopes = {
                @AuthorizationScope(scope = "write:department", description = "modify departments"),
                @AuthorizationScope(scope = "read:department", description = "read  departments")
            })
})
public class DepartmentResource extends AbstractFacade<Department> {

    @PersistenceContext(unitName = "HR_PU")
    private EntityManager em;

    public DepartmentResource() {
        super(Department.class);
    }

    @GET
    @Produces("application/json")
    @ApiOperation(value = "Find departments in the range",
            notes = "For valid response try with value <= 1  for from and two query parameters. Other values will generated exceptions",
            response = Department.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid range size supplied"),
        @ApiResponse(code = 404, message = "Department not found")
    })
    public List<Department> findAllDepartments(@DefaultValue("1") @QueryParam("from") Integer from, @DefaultValue("100") @QueryParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    @ApiOperation(value = "Find department by Id",
            notes = "For valid response try integer Ids with value <= 1 or > 50. Other values will generated exceptions",
            response = Department.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "Department not found")})
    public Department findDepartment(@PathParam("id") Short id) {
        return super.find(id);
    }

    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Create department",
            notes = "This can only be done by the logged in user.")
    public void createDepartment(@ApiParam(value = "Created department object", required = true) Department entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @ApiOperation(value = "Edit department",
            notes = "This can only be done by the logged in user.")
    public void editDepartment(@PathParam("id") Short id, @ApiParam(value = "Edit department object", required = true) Department entity) {
        super.edit(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

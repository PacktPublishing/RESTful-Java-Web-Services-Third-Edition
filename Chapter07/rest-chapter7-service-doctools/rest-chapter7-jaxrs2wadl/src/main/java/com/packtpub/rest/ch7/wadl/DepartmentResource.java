/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch7.wadl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
 * @author Jobinesh
 */
@Stateless
@Path("departments")
public class DepartmentResource extends AbstractFacade<Department> {

    @PersistenceContext(unitName = "HR_PU")
    private EntityManager em;

    public DepartmentResource() {
        super(Department.class);
    }

    @GET
    @Produces("application/json")
    public List<Department> findAllDepartments(@DefaultValue("1") @QueryParam("from") Integer from, @DefaultValue("100") @QueryParam("to") Integer to) {
          return super.findRange(new int[]{from, to});
    }
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Department findDepartment(@PathParam("id") Short id) {
        return super.find(id);
    }

    @POST
    @Consumes("application/json")
    public void createDepartment(Department entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public void editDepartment(@PathParam("id") Short id, Department entity) {
        super.edit(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
